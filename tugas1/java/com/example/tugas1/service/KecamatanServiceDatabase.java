package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KecamatanMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.service.KecamatanServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {

	 @Autowired
	 private KecamatanMapper kecamatanMapper;

	 @Override
	 public KecamatanModel selectKecamatan(String kode_kecamatan) {
		 log.info("select kecamatan with kode_kecamatan{}", kode_kecamatan);
		 return kecamatanMapper.selectKecamatan(kode_kecamatan);
	 }

	 
	 @Override
	 public KecamatanModel selectKecamatanID(String id) {
		 log.info("select kecamatan with id id{}", id);
		 return kecamatanMapper.selectKecamatanID(id);
	 }
	 

	 @Override
	 public List<KelurahanModel> selectKelurahan(String id) {
		 log.info("select from kelurahan where id kecamatan = id{}", id);
		 return kecamatanMapper.selectKelurahan(id);
	 }

	 
}
