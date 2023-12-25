package com.labcenter.auth;

import jakarta.json.bind.adapter.JsonbAdapter;
import java.util.List;
import java.util.stream.Collectors;

public class RolesAdapter implements JsonbAdapter<List<Role>, List<String>> {

    @Override
    public List<String> adaptToJson(List<Role> roles) throws Exception {
        return roles.stream()
                .map(Role::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> adaptFromJson(List<String> rolesList) throws Exception {
        return rolesList.stream()
                .map(Role::valueOf)
                .collect(Collectors.toList());
    }
}
