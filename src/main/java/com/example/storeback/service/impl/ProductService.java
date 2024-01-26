package com.example.storeback.service.impl;

import com.example.storeback.dto.obj.PageData;
import com.example.storeback.dto.response.ProductResponse;
import com.example.storeback.exception.NotFound;
import com.example.storeback.model.Category;
import com.example.storeback.model.Product;
import com.example.storeback.repository.CategoryRepository;
import com.example.storeback.repository.ProductRepository;
import com.example.storeback.service.IProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public ProductResponse getAll(HttpServletRequest request) {
        //test:?name=test&page=1&size=3&sort=+price
        String query=request.getQueryString();
        ProductResponse response=new ProductResponse();
        if(query==null){
            PageData pageData=new PageData();
            Pageable pageable = PageRequest.of(pageData.getNum(), pageData.getSize());
            Page<Product> products = productRepository.findAll(pageable);
            response.setProducts(products.getContent());
            response.setTotalPage(products.getTotalPages());
            response.setPageIndex(pageData.getNum()+1);
            return response;
        }
        Map<String,String> data=getFilterQuery(query);
        String size = data.get("size");
        String page = data.get("page");
        String field = data.get("sort");
        PageData pageData=new PageData();
        if(size!=null){
            pageData.setSize(Integer.parseInt(size));
            data.remove("size");
        }
        if(page!=null){
            pageData.setNum(Integer.parseInt(page)-1);
            data.remove("page");
        }
        if(field!=null){
            data.remove("sort");
        }

        Sort sortProduct=sortBy(field);
        Pageable pageable = PageRequest.of(pageData.getNum(), pageData.getSize(), sortProduct);
        Specification<Product> productSpecification=productSpecification(data);
        Page<Product> products = productRepository.findAll(productSpecification, pageable);
        response.setProducts(products.getContent());
        response.setTotalPage(products.getTotalPages());
        response.setPageIndex(pageData.getNum()+1);
        return response;
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> product= productRepository.findById(productId);
        if(product.isEmpty()){
            throw new NotFound("Not found product with id:"+productId);
        }
        return product.get();
    }

    @Override
    public Product createNewProduct(Product product,Long categoryId) {
        Optional<Category> category=categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            throw new NotFound("Not found category with id:"+categoryId);
        }
        product.setCategory(category.get());
        product.setReviewTotal(0.0);
        product.setReviewCount(0);

        return productRepository.save(product);
    }

    @Override
    public Product updateProductById(Long id, Product product) {
        Product updateProduct=getProductById(id);
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setImageCover(product.getImageCover());
        return productRepository.save(updateProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);

    }

    public Map<String,String> getFilterQuery(String query){
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("="))
                .filter(arr -> arr.length == 2)
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    public Sort sortBy(String sort){
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        Sort.Order order ;
        if (sort.startsWith("-")) {
            String field = sort.substring(1);
            order= Sort.Order.by(field);
            order = order.with(Sort.Direction.DESC);
        } else {
            order = Sort.Order.by(sort);
            order = order.with(Sort.Direction.ASC);
        }

        return Sort.by(order);
    }


    public <T> Specification<Product> productSpecification(Map<String, String> filter) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter != null) {
                filter.forEach((key, value) -> {
                    if (key != null && value != null) {
                        try {
                            Double numericValue = Double.parseDouble(value);

                            if ("categoryId".equals(key)) {
                                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);
                                predicates.add(builder.equal(categoryJoin.get("id"), numericValue));
                            }else {
                                predicates.add(builder.equal(root.get(key), numericValue));
                            }
                        } catch (NumberFormatException e) {
                            String valueIgnoreCase = value.toLowerCase();
                            predicates.add(builder.like(builder.lower(root.get(key)), "%" + valueIgnoreCase.replace("%20"," ") + "%"));

                        }
                    }
                });
            }


            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
    @Transactional(propagation = Propagation.NEVER)
    public void test(){
        try{
            SessionImplementor sessionImp = (SessionImplementor) entityManager.getDelegate();
            var transaction = sessionImp.getTransaction();
            transaction.begin();
            CriteriaBuilder builder=entityManager.getCriteriaBuilder();
            CriteriaQuery<Tuple> cq=builder.createTupleQuery();
            Root<Product> productRoot=cq.from(Product.class);
            Join<Product,Category> joinCategory= productRoot.join("category");
            cq.multiselect(productRoot,joinCategory);

            TypedQuery<Tuple> q= entityManager.createQuery(cq);

            q.getResultStream().forEach(t->{
                System.out.println(t.get(0).toString()+t.get(1));
            });
            transaction.commit();

        }finally {
            entityManager.close();
        }

    }
}
