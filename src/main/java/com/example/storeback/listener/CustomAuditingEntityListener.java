package com.example.storeback.listener;


import com.example.storeback.model.BaseEntity;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Configuration
@NoArgsConstructor
public class CustomAuditingEntityListener extends AuditingEntityListener {
    public CustomAuditingEntityListener(ObjectFactory<AuditingHandler> handler) {
        super.setAuditingHandler(handler);
    }



    @Override
    public void touchForCreate(Object target) {
        BaseEntity entity = (BaseEntity) target;
        if(entity.getCreatedAt()==null){
            super.touchForCreate(target);
        }else {
            if(entity.getUpdatedAt()==null){
                super.touchForUpdate(target);
            }
        }

    }

    @Override
    public void touchForUpdate(Object target) {
        super.touchForUpdate(target);
    }
}
