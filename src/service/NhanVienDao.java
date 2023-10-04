/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import repository.JdbcHelper;
import java.sql.ResultSet;

public class NhanVienDao extends EduSysDao<NhanVien, String> {

    String insert_sql = """
                        INSERT INTO [dbo].[NhanVien]
                                              ([MaNV]
                                              ,[MatKhau]
                                              ,[HoTen]
                                              ,[VaiTro])
                        VALUES (?, ?, ?, ?)
                        """;
    String update_sql = """
                        UPDATE [dbo].[NhanVien]
                            SET [MatKhau] = ?
                               ,[HoTen] = ?
                               ,[VaiTro] = ?
                        WHERE MaNV = ?
                        """;
    String delete_sql = """
                        DELETE FROM [dbo].[NhanVien]
                        WHERE MaNV = ?
                        """;
    String select_all = """
                        select * from NhanVien
                        """;
    String selectById = """
                        select * from NhanVien
                        WHERE MaNV = ?
                        """;

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(insert_sql,
                entity.getMaNV(), entity.getMatKhau(),
                entity.getHoTen(), entity.getVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(update_sql,
                entity.getMatKhau(), entity.getHoTen(),
                entity.getVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {

            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(nv);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(selectById, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(select_all);
    }

}
