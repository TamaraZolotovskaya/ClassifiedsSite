package ru.tamara.classifiedsSite.dto;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER/*(Set.of(Permission.USER_GET_AD_LIST))*/,
    ADMIN/*(Set.of(Permission.USER_GET_AD_LIST,
            Permission.USER_GET_AD))*/;

//    private final Set<Permission> permissions;

//    Role(Set<Permission> permissions) {
//        this.permissions = permissions;
//    }

//    public Set<Permission> getPermissions() {
//        return permissions;
//    }

//    public Set<SimpleGrantedAuthority> getAuthorities() {
//        return getPermissions().stream()
//                .map((permission -> new SimpleGrantedAuthority(permission.getPermission())))
//                .collect(Collectors.toSet());
//    }

    @Override
    public String getAuthority() {
        return name();
    }
}
