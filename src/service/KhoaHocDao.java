/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.KhoaHoc;
import repository.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ledin
 */
public class KhoaHocDao extends EduSysDao<KhoaHoc, Integer> {

    String insert_sql = """
                 INSERT INTO [dbo].[KhoaHoc]
                            ([MaCD]
                            ,[HocPhi]
                            ,[ThoiLuong]
                            ,[NgayKG]
                            ,[GhiChu]
                            ,[MaNV]
                            ,[NgayTao])
                      VALUES
                            (?,?,?,?,?,?,?)
                 """;

    String update_sql = """
          UPDATE [dbo].[KhoaHoc]
                       SET [MaCD] = ?
                          ,[HocPhi] = ?
                          ,[ThoiLuong] = ?
                          ,[NgayKG] = ?
                          ,[GhiChu] = ?
                          ,[MaNV] = ?
                          ,[NgayTao] = ?
                     WHERE MaKH = ?
                    """;

    String delete_sql = """
                        DELETE FROM [dbo].[KhoaHoc]
                              WHERE MaKH = ?
                        """;

    String selectById = """
                        select * from KhoaHoc
                        where MaKH = ?
                         """;

    String selectAll = """
                       select * from KhoaHoc
                       """;

    @Override
    public void insert(KhoaHoc entity) {
        JdbcHelper.update(insert_sql,
                entity.getMaCD(), entity.getHocPhi(),
                entity.getThoiLuong(), entity.getNgayKG(),
                entity.getGhiChu(), entity.getMaNV(),
                entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        JdbcHelper.update(update_sql,
                entity.getMaCD(), entity.getHocPhi(),
                entity.getThoiLuong(), entity.getNgayKG(),
                entity.getGhiChu(), entity.getMaNV(),
                entity.getNgayTao(), entity.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = this.selectBySql(selectById, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(selectAll);
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getInt("MaKH"));
                kh.setMaCD(rs.getString("MaCD"));
                kh.setHocPhi(rs.getDouble("HocPhi"));
                kh.setThoiLuong(rs.getInt("ThoiLuong"));
                kh.setNgayKG(rs.getDate("NgayKG"));
                kh.setGhiChu(rs.getString("GhiChu"));
                kh.setMaNV(rs.getString("MaNV"));
                kh.setNgayTao(rs.getDate("NgayTao"));
                list.add(kh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<KhoaHoc> selectByChuyenDe(String maCD) {
        String sql = """
                     SELECT * FROM KhoaHoc
                     WHERE  MaCD = ?
                     """;
        return this.selectBySql(sql, maCD);
    }

    public List<Integer> selectYears() {
        String sql = """
                    SELECT DISTINCT YEAR(NgayKG) AS Year
                    FROM KhoaHoc 
                    ORDER BY Year DESC
                     """;

        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
