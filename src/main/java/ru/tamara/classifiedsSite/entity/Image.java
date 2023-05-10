package ru.tamara.classifiedsSite.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс, описывающий изображения - аватары для пользователя {@link User} и фото для объявлений {@link Ad}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    private String id;

    @Lob
    @Column(name = "image_path")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] imagePath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Image image = (Image) o;
        return id != null && Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
