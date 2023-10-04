/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.ChuyenDe;
import repository.JdbcHelper;
import java.sql.ResultSet;

/**
 *
 * @author ledin
 */
public class ChuyenDeDao extends EduSysDao<ChuyenDe, String> {

    String insert_sql = """
                        INSERT INTO [dbo].[ChuyenDe]
                                   ([MaCD]
                                   ,[TenCD]
                                   ,[HocPhi]
                                   ,[ThoiLuong]
                                   ,[Hinh]
                                   ,[MoTa])
                             VALUES
                                   (?,?,?,?,?,?)
                        """;
    String update_sql = """
                        UPDATE [dbo].[ChuyenDe]
                                  SET [TenCD] = ?
                                     ,[HocPhi] = ?
                                     ,[ThoiLuong] = ?
                                     ,[Hinh] = ?
                                     ,[MoTa] = ?
                        WHERE MaCD = ?
                        """;
    String delete_sql = """
                        DELETE FROM [dbo].[ChuyenDe]
                        WHERE MaCD = ?
                        """;
    String select_all = """
                        SELECT * FROM [dbo].[ChuyenDe]
                        """;
    String selectById = """
                        SELECT * FROM [dbo].[ChuyenDe]
                        WHERE MaCD = ?
                        """;

    @Override
    public void insert(ChuyenDe entity) {
        JdbcHelper.update(insert_sql,
                entity.getMaCD(), entity.getTenCD(),
                entity.getHocPhi(), entity.getThoiLuong(),
                entity.getHinhAnh(), entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        JdbcHelper.update(update_sql,
                entity.getTenCD(), entity.getHocPhi(),
                entity.getThoiLuong(), entity.getHinhAnh(),
                entity.getMoTa(), entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString("MaCD"));
                cd.setTenCD(rs.getString("TenCD"));
                cd.setHocPhi(rs.getDouble("HocPhi"));
                cd.setThoiLuong(rs.getInt("ThoiLuong"));
                cd.setHinhAnh(rs.getString("Hinh"));
                cd.setMoTa(rs.getString("MoTa"));
                list.add(cd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public ChuyenDe selectById(String id) {
        List<ChuyenDe> list = this.selectBySql(selectById, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(select_all);
    }

}
