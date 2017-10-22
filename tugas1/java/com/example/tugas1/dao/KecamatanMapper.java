package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KecamatanMapper {

	@Select("select * from tugas1.kecamatan where kode_kecamatan = #{kode_kecamatan}")
	KecamatanModel selectKecamatan (@Param("kode_kecamatan") String kode_kecamatan);
	
	@Select("select * from tugas1.kecamatan where id = #{id}")
	KecamatanModel selectKecamatanID (@Param("id") String id);
	
	@Select("select * from tugas1.kelurahan where kelurahan.id_kecamatan = #{id}")
	List<KelurahanModel> selectKelurahan(@Param("id") String id);
}
