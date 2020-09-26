package guperstudy.design.template.rowmapper;

import java.sql.ResultSet;

/**
 * @author joe
 * @program: design
 * @description: 结果集映射接口
 * @date 2020-09-26 16:08:36
 */
public interface RowMapper<T> {
    /**
     * 映射结果集
     * @param rs 结果集对象
     * @param rowNum 行数
     * @param <T> 映射实体类型
     * @return 映射实体
     * @throws Exception 异常
     */
    T mapRow(ResultSet rs, int rowNum) throws Exception;
}
