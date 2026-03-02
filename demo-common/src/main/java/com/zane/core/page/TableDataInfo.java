package com.zane.core.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class TableDataInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    // 总记录数
    private long total;

    // 列表数据
    private List<T> records;

    // 状态码
    private int code;

    // 提示信息
    private String msg;

    /**
     * 整个列表数据
     * @param lists
     * @return
     */
    public static <T> TableDataInfo<T> build(List<T> lists) {
        TableDataInfo<T> td = new TableDataInfo<T>();
        td.setRecords(lists);
        td.setTotal(lists.size());
        td.setCode(200);
        td.setMsg("查询成功");
        return td;
    }

    /**
     * 分页数据
     * @param page
     * @return
     */
    public static <T> TableDataInfo<T> build(IPage<T> page) {
        TableDataInfo<T> td = new TableDataInfo<T>();
        td.setRecords(page.getRecords());
        td.setTotal(page.getTotal());
        td.setCode(200);
        td.setMsg("查询成功");
        return td;
    }
}
