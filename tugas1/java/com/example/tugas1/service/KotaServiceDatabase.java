package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tugas1.dao.KotaMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.service.KotaServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService {


	 @Autowired
	 private KotaMapper kotaMapper;

	 @Override
	 public List<KotaModel> selectKota() {
		 log.info("select all kota");
		 return kotaMapper.selectKota();
	 }
	 
	 @Override
	 public List<KecamatanModel> selectKecamatan(String id) {
		 log.info("select from kecamatan where id kota id{}", id);
		 return kotaMapper.selectKecamatan(id);
	 }
	 
	 @Override
	 public KotaModel selectKotaID(String id) {
		 log.info("select kota with id id{}", id);
		 return kotaMapper.selectKotaID(id);
	 }
		
	 
	 
	 
}
