/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.ArrayList;
import java.util.List;
import model.NguoiHoc;
import repository.JdbcHelper;
import java.sql.ResultSet;

/**
 *
 * @author ledin
 */
public class NguoiHocDao extends EduSysDao<NguoiHoc, String> {

    String insert_sql = """
                        INSERT INTO [dbo].[NguoiHoc]
                                   ([MaNH]
                                   ,[HoTen]
                                   ,[NgaySinh]
                                   ,[GioiTinh]
                                   ,[DienThoai]
                                   ,[Email]
                                   ,[GhiChu]
                                   ,[MaNV]
                                   ,[NgayDK])
                             VALUES
                                   (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """;
    String update_sql = """
                        UPDATE [dbo].[NguoiHoc]
                           SET 
                               [HoTen] = ?
                              ,[NgaySinh] = ?
                              ,[GioiTinh] = ?
                              ,[DienThoai] = ?
                              ,[Email] = ?
                              ,[GhiChu] = ?
                              ,[MaNV] = ?
                              ,[NgayDK] = ?
                         WHERE [MaNH] = ?
                        """;
    String delete_sql = """
                        DELETE FROM [dbo].[NguoiHoc]
                        WHERE MaNH = ?
                        """;
    String select_all = """
                        SELECT * FROM NguoiHoc
                        """;
    String selectById = """
                        SELECT * FROM NguoiHoc
                        WHERE MaNH = ?
                        """;

    @Override
    public void insert(NguoiHoc entity) {
        JdbcHelper.update(insert_sql,
                entity.getMaNH(), entity.getHoTen(), entity.getNgaySinh(),
                entity.getGioiTinh(), entity.getDienThoai(), entity.getEmail(),
                entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void update(NguoiHoc entity) {
        JdbcHelper.update(update_sql,
                entity.getHoTen(), entity.getNgaySinh(), entity.getGioiTinh(),
                entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(),
                entity.getMaNV(), entity.getNgayDK(), entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(delete_sql, id);
    }

    @Override
    public NguoiHoc selectById(String id) {
        List<NguoiHoc> list = this.selectBySql(selectById, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(select_all);
    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString("MaNH"));
                nh.setHoTen(rs.getString("HoTen"));
                nh.setNgaySinh(rs.getDate("NgaySinh"));
                nh.setGioiTinh(rs.getBoolean("GioiTinh"));
                nh.setDienThoai(rs.getString("DienThoai"));
                nh.setEmail(rs.getString("Email"));
                nh.setGhiChu(rs.getString("GhiChu"));
                nh.setMaNV(rs.getString("MaNV"));
                nh.setNgayDK(rs.getDate("NgayDK"));
                list.add(nh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<NguoiHoc> selectByKeyWord(String keyWrod) {
        String sql = """
                     SELECT * FROM NguoiHoc WHERE HoTen LIKE ?
                     """;
        return this.selectBySql(sql, "%" + keyWrod + "%");
    }
    
    public List<NguoiHoc> selectNotInCourse(Integer maKH, String keyWord){
        String sql = """
                     select * from NguoiHoc
                     where HoTen like ? and MaNH not in (
                     select MaNH from HocVien
                     where MaKH = ?
                     )
                     """;
        return this.selectBySql(sql, "%" + keyWord + "%", maKH);
    }
}
