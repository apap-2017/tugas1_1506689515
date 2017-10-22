package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KeluargaMapper {

	@Select("select * from tugas1.keluarga where nomor_kk = #{nomor_kk}")
	KeluargaModel selectKeluarga (@Param("nomor_kk") String nomor_kk);
	
	@Select("select * from tugas1.keluarga where id = #{id}")
	KeluargaModel selectKeluargaID (@Param("id") String id);
	
	@Select("select * from tugas1.keluarga where nomor_kk LIKE #{nkkdepan}")
    List<KeluargaModel> selectWithNKK(@Param("nkkdepan") String nkkdepan);
	
	@Insert("insert into tugas1.keluarga (nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) values (#{nomor_kk}, #{alamat}, #{RT}, #{RW}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void addKeluarga(KeluargaModel keluarga);

	@Update("update keluarga set nomor_kk = #{nomor_kk}, alamat = #{alamat}, RT = #{RT}, RW = #{RW}, id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} where id = #{id}")
	void ubahKeluarga(KeluargaModel keluarga);
	
	@Select("select penduduk.id, penduduk.nik, penduduk.nama, penduduk.tempat_lahir, "
			+ "penduduk.tanggal_lahir, penduduk.jenis_kelamin, penduduk.is_wni, "
			+ "penduduk.id_keluarga, penduduk.agama, penduduk.pekerjaan, "
			+ "penduduk.status_perkawinan, penduduk.status_dalam_keluarga, "
			+ "penduduk.golongan_darah, penduduk.is_wafat"
			+ "	from penduduk,keluarga"
			+ "	where keluarga.id = #{idparam}"
			+ "	and keluarga.id = penduduk.id_keluarga")
    List<PendudukModel> selectAnggota (@Param("idparam") String idparam);
	
	@Select("select penduduk.id, penduduk.nik, penduduk.nama, penduduk.tempat_lahir, "
			+ "penduduk.tanggal_lahir, penduduk.jenis_kelamin, penduduk.is_wni, "
			+ "penduduk.id_keluarga, penduduk.agama, penduduk.pekerjaan, "
			+ "penduduk.status_perkawinan, penduduk.status_dalam_keluarga, "
			+ "penduduk.golongan_darah, penduduk.is_wafat"
			+ "	from penduduk,keluarga"
			+ "	where keluarga.id = #{idparam}"
			+ "	and keluarga.id = penduduk.id_keluarga "
			+ "and penduduk.is_wafat = '0'")
    List<PendudukModel> selectAnggotaHidup (@Param("idparam") String idparam);
  
    
	
	
}
