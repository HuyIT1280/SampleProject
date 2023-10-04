/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.HocVien;
import repository.JdbcHelper;
import java.sql.ResultSet;

/**
 *
 * @author ledin
 */
public class HocVienDao extends EduSysDao<HocVien, Integer> {

    String insert_sql = """
                        INSERT INTO [dbo].[HocVien]
                                   ([MaKH]
                                   ,[MaNH]
                                   ,[Diem])
                        VALUES (?, ?, ?)
                        """;

    String update_sql = """
                       UPDATE [dbo].[HocVien]
                            SET [Diem] = ?
                          WHERE MaHV = ?
                        """;
    String delete_sql = """
                        DELETE FROM [dbo].[HocVien]
                              WHERE MaHV = ?
                        """;
    String selectById = """
                        select * from HocVien where MaHV = ?
                        """;
    String selectAll = """
                       select * from HocVien
                        """;

    @Override
    public void insert(HocVien entity) {
        JdbcHelper.update(insert_sql,
                entity.getMaKH(),
                entity.getMaNH(),
                entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        JdbcHelper.update(update_sql,
                entity.getDiem(),
                entity.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = this.selectBySql(selectById, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(selectAll);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HocVien hv = new HocVien();
                hv.setMaHV(rs.getInt("MaHV"));
                hv.setMaKH(rs.getInt("MaKH"));
                hv.setMaNH(rs.getString("MaNH"));
                hv.setDiem(rs.getDouble("Diem"));
                list.add(hv);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<HocVien> selectByKhoaHoc(Integer maKH) {
        String sql = """
                     select * from HocVien where MaKH = ?
                     """;
        return this.selectBySql(sql, maKH);
    }
}
