package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.PendudukServiceDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {

	 @Autowired
	 private PendudukMapper pendudukMapper;

	 @Override
	 public PendudukModel selectPenduduk(String nik) {
		 log.info("select penduduk with nik{}", nik);
		 return pendudukMapper.selectPenduduk(nik);
	 }
	 
	 @Override
	 public List<PendudukModel> selectWithNIK(String nikdepan) {
		 log.info("select list of penduduk with nikdepan{}", nikdepan);
		 return pendudukMapper.selectWithNIK(nikdepan);
	
	 } 
	 
	 @Override
	 public void addPenduduk(PendudukModel penduduk) {
		
		  pendudukMapper.addPenduduk(penduduk);
	 
	 }
	 
	 @Override
	 public void ubahPenduduk (PendudukModel penduduk) {
		 
	   	log.info("penduduk" + penduduk.getNik() + "updated");
	    pendudukMapper.ubahPenduduk(penduduk);
	    	
	 }

	 @Override
	 public List<PendudukModel> selectPendudukKelurahan(String idparam) {
		 log.info("select penduduk dengan id kelurahan idparam{}", idparam);
		 return pendudukMapper.selectPendudukKelurahan(idparam);
	 }


}
