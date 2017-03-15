package by.haidash.blog.server.model.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by haidash on 10.03.17.
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "comment")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id", "text", "post"})
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long id;

    @ManyToOne
    @CreatedBy
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Comment parent;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "text", nullable = false)
    private String text;

    @CreatedDate
    @Column(name = "created_on")
    private LocalDateTime createdDate;
}
