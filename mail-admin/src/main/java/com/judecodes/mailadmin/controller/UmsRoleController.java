package com.judecodes.mailadmin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.judecodes.mailadmin.domain.entity.Role;
import com.judecodes.mailadmin.domain.service.RoleService;

import com.judecodes.mailadmin.param.CreateRoleParam;
import com.judecodes.mailadmin.param.UpdateRoleParam;
import com.judecodes.mailadmin.vo.MenuBasicInfoVO;
import com.judecodes.mailadmin.vo.ResourceBasicInfoVO;
import com.judecodes.mailadmin.vo.RoleInfoVO;
import com.judecodes.mailweb.vo.PageResult;
import com.judecodes.mailweb.vo.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author judecodes
 * @since 2025-11-26
 */
@RestController
@Tag(name = "UmsRoleController", description = "后台用户角色管理")
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid CreateRoleParam param) {
        Role role = new Role();
        BeanUtil.copyProperties(param, role);
        roleService.createRole(role);
        return Result.success(true);
    }

    @PostMapping("/update/{id}")
    public Result<Boolean> update(@PathVariable Long id,
                                  @RequestBody @Valid UpdateRoleParam param) {
        Role role = new Role();
        BeanUtil.copyProperties(param, role);
        role.setId(id);
        roleService.updateRole(role);
        return Result.success(true);
    }

    @PostMapping("/delete/{id}")
    public Result<Boolean> delete(@PathVariable  Long id) {

        roleService.deleteRole(id);

        return Result.success(true);
    }

    @PostMapping("/delete/batch")
    public Result<Boolean> deleteBatch(@RequestBody @NotEmpty List<Long> ids) {
         roleService.deleteBatch(ids);

        return Result.success(true);
    }
    @GetMapping("/role/list")
    public PageResult<RoleInfoVO> list(@RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) String state,
                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {


        return PageResult.successMulti(null, 0, 0,0);
    }

    @PostMapping("/updateStatus/{id}")
    public Result<Boolean> updateStatus(@PathVariable Long id,
                                        @RequestParam Integer status) {

        return Result.success(true);
    }

    @GetMapping("/resource/{roleId}")
    public Result<List<ResourceBasicInfoVO>> listResource(@PathVariable Long roleId) {

        return Result.success(null);
    }

    @PostMapping("/allocResource")
    public Result<Boolean> allocResource(@RequestParam Long roleId,
                                         @RequestBody List<Long> resourceIds) {

        return Result.success(true);
    }




    @GetMapping("/menu/{roleId}")
    public Result<List<MenuBasicInfoVO>> listMenu(@PathVariable Long roleId) {

        return Result.success(null);
    }

    @PostMapping("/menu/allocMenu")
    public Result<Boolean> allocMenu(@RequestParam Long roleId,
                                     @RequestBody List<Long> menuIds) {

        return Result.success(true);
    }



}
