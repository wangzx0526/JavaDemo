package com.zane.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.ISysDeptService;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.DeptQueryDto;
import com.zane.entity.SysDept;
import com.zane.entity.SysUser;
import com.zane.mapper.SysDeptMapper;
import com.zane.mapper.SysUserMapper;
import com.zane.vo.SysDeptTreeVo;
import com.zane.vo.SysDeptVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wzx
* @description 针对表【sys_dept(部门信息表)】的数据库操作Service实现
* @createDate 2026-02-16 10:09:32
*/

@Slf4j
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    private final SysDeptMapper deptMapper;
    private final SysUserMapper userMapper;

    @Override
    public List<SysDeptVo> getDeptList(String deptName, Integer status) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptName), SysDept::getDeptName, deptName)
                .eq(status != null, SysDept::getStatus, status);
        List<SysDept> deptList = deptMapper.selectList(queryWrapper);

        List<SysDeptVo> result = BeanUtil.copyToList(deptList, SysDeptVo.class);
        return result;
    }

    @Override
    public TableDataInfo<SysDeptVo> getDeptPageList(Page page, DeptQueryDto deptQueryDto) {
        if (deptQueryDto == null) {
            deptQueryDto = new DeptQueryDto();
        }
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptQueryDto.getDeptName()), SysDept::getDeptName, deptQueryDto.getDeptName())
                .eq(deptQueryDto.getStatus() != null, SysDept::getStatus, deptQueryDto.getStatus());
        IPage<SysDept> resultPage = deptMapper.selectPage(page, queryWrapper);

        IPage<SysDeptVo> result = resultPage.convert(dept -> {
            SysDeptVo vo = new SysDeptVo();
            BeanUtil.copyProperties(dept, vo);
            return vo;
        });
        return TableDataInfo.build(result);
    }

    @Override
    public SysDeptVo getDeptById(Long id) {
        if (id == null) {
            return null;
        }
        SysDept dept = deptMapper.selectById(id);
        return dept == null ? null : BeanUtil.toBean(dept, SysDeptVo.class);
    }

    @Override
    public boolean addDept(SysDept dept) {
        if (dept == null) {
            return false;
        }
        if (dept.getParentId() != null && dept.getParentId() != 0) {
            if (deptMapper.selectById(dept.getParentId()) == null) {
                log.warn("新增部门失败：父部门不存在 parentId={}", dept.getParentId());
                return false;
            }
        }
        return deptMapper.insert(dept) > 0;
    }

    @Override
    public boolean updateDept(SysDept dept) {
        if (dept == null || dept.getId() == null) {
            return false;
        }
        if (deptMapper.selectById(dept.getId()) == null) {
            return false;
        }
        if (dept.getParentId() != null && dept.getParentId().equals(dept.getId())) {
            log.warn("修改部门失败：父部门不能为自身 id={}", dept.getId());
            return false;
        }
        if (dept.getParentId() != null && dept.getParentId() != 0) {
            List<Long> descendantIds = getDescendantIds(dept.getId());
            if (descendantIds.contains(dept.getParentId())) {
                log.warn("修改部门失败：父部门不能为自身或子部门 id={}, parentId={}", dept.getId(), dept.getParentId());
                return false;
            }
            if (deptMapper.selectById(dept.getParentId()) == null) {
                log.warn("修改部门失败：父部门不存在 parentId={}", dept.getParentId());
                return false;
            }
        }
        return deptMapper.updateById(dept) > 0;
    }

    /** 获取某部门下所有子部门ID（含多级） */
    private List<Long> getDescendantIds(Long deptId) {
        List<Long> result = new ArrayList<>();
        List<SysDept> all = deptMapper.selectList(null);
        List<Long> currentLevel = new ArrayList<>();
        currentLevel.add(deptId);
        while (!currentLevel.isEmpty()) {
            final List<Long> level = currentLevel;
            List<Long> nextLevel = all.stream()
                    .filter(d -> d.getParentId() != null && level.contains(d.getParentId()))
                    .map(SysDept::getId)
                    .collect(Collectors.toList());
            result.addAll(nextLevel);
            currentLevel = nextLevel;
        }
        return result;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        if (deptMapper.selectById(id) == null) {
            return false;
        }
        long childCount = deptMapper.selectCount(
                new LambdaQueryWrapper<SysDept>().eq(SysDept::getParentId, id));
        if (childCount > 0) {
            log.warn("删除部门失败：存在子部门，无法删除 id={}", id);
            return false;
        }
        long userCount = userMapper.selectCount(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getDeptId, id));
        if (userCount > 0) {
            log.warn("删除部门失败：该部门下存在用户，无法删除 id={}", id);
            return false;
        }
        return deptMapper.deleteById(id) > 0;
    }

    @Override
    public List<SysDeptTreeVo> getDeptTree() {
        List<SysDept> deptList = deptMapper.selectList(null);
        List<SysDeptTreeVo> result = BeanUtil.copyToList(deptList, SysDeptTreeVo.class);
        return result;
    }
}


