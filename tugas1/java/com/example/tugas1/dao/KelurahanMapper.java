package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KelurahanMapper {

	@Select("select * from tugas1.kelurahan where kode_kelurahan = #{kode_kelurahan}")
	KelurahanModel selectKelurahan (@Param("kode_kelurahan") String kode_kelurahan);
	
	@Select("select * from tugas1.kelurahan where id = #{id}")
	KelurahanModel selectKelurahanID (@Param("id") String id);
	
	@Select("select * from tugas1.keluarga where keluarga.id_kelurahan = #{id}")
	List<KeluargaModel> selectKeluarga (@Param("id") String id);
	
	@Select("SELECT * from penduduk " + 
			"WHERE penduduk.tanggal_lahir IN " + 
			" (" + 
			" 	SELECT MIN(tanggal_lahir) " + 
			"    FROM penduduk,keluarga " + 
			"    WHERE penduduk.id_keluarga = keluarga.id " + 
			"    AND keluarga.id_kelurahan = #{idparam}" +  
			" )")
	List<PendudukModel> selectPendudukTertua(@Param("idparam") String idparam);
	
	@Select("SELECT * from penduduk " + 
			"WHERE penduduk.tanggal_lahir IN " + 
			" (" + 
			" 	SELECT MAX(tanggal_lahir) " + 
			"    FROM penduduk,keluarga " + 
			"    WHERE penduduk.id_keluarga = keluarga.id " + 
			"    AND keluarga.id_kelurahan = #{idparam}" +  
			" )")
	List<PendudukModel> selectPendudukTermuda(@Param("idparam") String idparam);
	
}
