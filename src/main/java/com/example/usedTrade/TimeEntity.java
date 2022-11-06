package com.example.usedTrade;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class TimeEntity {

    @CreatedDate
    @Column(name = "regDt", updatable = false)
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column(name = "upDt")
    private LocalDateTime upDt;

}
