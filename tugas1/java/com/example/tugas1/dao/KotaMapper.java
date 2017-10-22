package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;


@Mapper
public interface KotaMapper {

	@Select("select * from kota")
	List<KotaModel> selectKota();
	
	@Select("select * from kecamatan where kecamatan.id_kota = #{idparam}")
	List<KecamatanModel> selectKecamatan(@Param("idparam") String idparam);

	@Select("select * from kota where kota.id = #{id}")
	KotaModel selectKotaID(@Param("id")String id);

}
