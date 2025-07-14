package com.example.myspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Cesium实体类，用于表示Cesium相关数据
 */
@Table(name = "cesium")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Cesium {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称，不能为空
     */
    @Column(nullable = false)
    private String name;

    /**
     * 点的数量，int类型，不能为空
     */
    @Column(nullable = false,name="pointNum")
    private Integer pointNum;

    /**
     * 时间，int类型，不能为空
     */
    @Column(nullable = false)
    private Integer time;

    /**
     * 状态，int类型，不能为空
     */
    @Column(nullable = false)
    private Integer status;

    /**
     * 速度，string类型，不能为空
     */
    @Column(nullable = false)
    private String speed;

    /**
     * 总距离，string类型，不能为空
     */
    @Column(nullable = false)
    private String trackmileage;

    /**
     * 创建时间，不可更新
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


    /**
     * 关联的临时航点集合，与Waypoint实体类的route属性关联
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")  // 指定外键字段
    //@JsonIgnore //直接忽略会导致循环的属性 可以不加载关联的子表,不关联的话新增也没有哦
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // 只允许写不允许读
    private Set<Waypoint> tempWaypoints = new HashSet<>();
}
