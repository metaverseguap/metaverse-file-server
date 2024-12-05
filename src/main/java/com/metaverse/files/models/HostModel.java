package com.metaverse.files.models;

import java.util.Collections;
import java.util.List;

import com.metaverse.files.security.models.UserModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Hibernate модель представляющая объект host.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Entity
@Table(name = "hosts")
public class HostModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uri")
    private String uri;

    @OneToMany(mappedBy = "hostModel", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserModel> userModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_id", referencedColumnName = "id")
    private SceneModel sceneModel;

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return url
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri url
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return пользователь являющийся данным хостом
     */
    public List<UserModel> getUser() {
        return userModel;
    }

    /**
     * Добавляет пользователя являющегося хостом.
     *
     * @param userModel {@link UserModel пользователь} являющийся данным хостом
     */
    public void setUser(UserModel userModel) {
        this.userModel = Collections.singletonList(userModel);
        userModel.setHostModel(this);
    }

    /**
     * @return {@link SceneModel}
     */
    public SceneModel getScene() {
        return sceneModel;
    }

    /**
     * @param sceneModel {@link SceneModel}
     */
    public void setScene(SceneModel sceneModel) {
        this.sceneModel = sceneModel;
    }
}
