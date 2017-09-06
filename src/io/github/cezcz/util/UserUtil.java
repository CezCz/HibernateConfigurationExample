package io.github.cezcz.util;

import io.github.cezcz.hibernate.UserEntity;
import io.github.cezcz.soap.User;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Cezary on 27.04.2017.
 */
public class UserUtil {

    public static UserEntity validateUserCredentials(User user, Session session) {
        String login = user.login;
        String password = user.password;

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot = criteria.from(UserEntity.class);
        criteria.select(userEntityRoot);
        criteria.where(criteriaBuilder.equal(userEntityRoot.get("login"), login));
        criteria.where(criteriaBuilder.equal(userEntityRoot.get("password"), password));

        return session.createQuery(criteria).getSingleResult();
    }
}
