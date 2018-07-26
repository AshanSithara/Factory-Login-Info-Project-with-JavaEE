package com.proje.service;

import com.proje.dao.RoleDao;
import com.proje.dto.RoleDto;
import com.proje.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Transactional(readOnly = true)
    public List<RoleDto> findAll(){

        List<RoleDto> roleDtos = new ArrayList<RoleDto>();
        try {
            List<Roles> roles = roleDao.findAllRoles();

            for(Roles roles1:roles){

                roleDtos.add(new RoleDto(roles1));
            }

        }catch (Exception e){
            return null;
        }
        return roleDtos;
    }
}
