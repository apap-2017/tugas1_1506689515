package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
	@Select("select * from tugas1.penduduk where nik = #{nik}")
	PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select * from penduduk where nik LIKE #{nikdepan}")
    List<PendudukModel> selectWithNIK(@Param("nikdepan") String nikdepan);
	
	@Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) values (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk(PendudukModel penduduk);

	@Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} where id = #{id}")
	void ubahPenduduk (PendudukModel penduduk);
	
	@Select("select * from penduduk, keluarga "
			+ "where penduduk.id_keluarga = keluarga.id "
			+ "and keluarga.id_kelurahan = #{idparam}")
	List<PendudukModel> selectPendudukKelurahan(@Param("idparam") String idparam);

}
