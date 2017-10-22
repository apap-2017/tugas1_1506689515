package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KeluargaServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {

	 @Autowired
	 private KeluargaMapper keluargaMapper;

	 @Override
	 public KeluargaModel selectKeluarga(String nomor_kk) {
		 log.info("select keluarga with nomor_kk{}", nomor_kk);
		 return keluargaMapper.selectKeluarga(nomor_kk);
	 }

	 
	 @Override
	 public KeluargaModel selectKeluargaID(String id) {
		 log.info("select keluarga with id id{}", id);
		 return keluargaMapper.selectKeluargaID(id);
	 }
	 
	 @Override
	 public List<KeluargaModel> selectWithNKK(String nkkdepan) {
		 return keluargaMapper.selectWithNKK(nkkdepan + "%");
	 }
	 
	 @Override
	 public void addKeluarga(KeluargaModel keluarga) {
		
		  keluargaMapper.addKeluarga(keluarga);
	 
	 }

	 @Override
	 public void ubahKeluarga (KeluargaModel keluarga) {
		 
	   	log.info("keluarga" + keluarga.getNomor_kk() + "updated");
	    keluargaMapper.ubahKeluarga(keluarga);
	    	
	 }
	 
	 @Override
	 public List<PendudukModel> selectAnggota (String id) {
		 return keluargaMapper.selectAnggota(id);
	 }

	 @Override
	 public List<PendudukModel> selectAnggotaHidup (String id) {
		 return keluargaMapper.selectAnggotaHidup(id);
	 }
	    
}
