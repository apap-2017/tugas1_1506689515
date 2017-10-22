package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KelurahanMapper;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.service.KelurahanServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {

	 @Autowired
	 private KelurahanMapper kelurahanMapper;

	 @Override
	 public KelurahanModel selectKelurahan(String kode_kelurahan) {
		 log.info("select kelurahan with kode_kelurahan{}", kode_kelurahan);
		 return kelurahanMapper.selectKelurahan(kode_kelurahan);
	 }

	 
	 @Override
	 public KelurahanModel selectKelurahanID(String id) {
		 log.info("select kelurahan with id id{}", id);
		 return kelurahanMapper.selectKelurahanID(id);
	 }

	 @Override
	 public List<KeluargaModel> selectKeluarga(String id) {
		 log.info("select keluarga where id_kelurahan id{}", id);
		 return kelurahanMapper.selectKeluarga(id);
	 } 

	@Override
	public List<PendudukModel> selectPendudukTermuda(String idparam) {
		log.info("select penduduk termuda dari kelurahan id{}");
		return kelurahanMapper.selectPendudukTermuda(idparam);
	}
	
	@Override
	public List<PendudukModel> selectPendudukTertua(String idparam) {
		log.info("select penduduk tertua dari kelurahan id{}");
		return kelurahanMapper.selectPendudukTertua(idparam);
	}
}
