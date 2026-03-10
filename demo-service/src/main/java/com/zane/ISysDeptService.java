package com.zane;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zane.core.page.TableDataInfo;
import com.zane.dto.DeptQueryDto;
import com.zane.entity.SysDept;
import com.zane.vo.SysDeptTreeVo;
import com.zane.vo.SysDeptVo;

import java.util.List;

/**
* @author wzx
* @description 针对表【sys_dept(部门信息表)】的数据库操作Service
* @createDate 2026-02-16 10:09:32
*/
public interface ISysDeptService {
    /**
     * 获取部门列表
     * @return
     */
    List<SysDeptVo> getDeptList(String deptName, Integer status);

    /**
     * 分页查询部门
     * @param page
     * @param deptQueryDto
     * @return
     */
    TableDataInfo<SysDeptVo> getDeptPageList(Page page, DeptQueryDto deptQueryDto);

    /**
     * 根据ID获取部门
     * @param id
     * @return
     */
    SysDeptVo getDeptById(Long id);

    /**
     * 新增部门
     * @param dept
     * @return
     */
    boolean addDept(SysDept dept);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    boolean updateDept(SysDept dept);

    /**
     * 删除部门
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    List<SysDeptTreeVo> getDeptTree();
}
