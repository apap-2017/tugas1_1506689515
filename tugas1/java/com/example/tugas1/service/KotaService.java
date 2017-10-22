package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;

public interface KotaService {
	List<KotaModel> selectKota();
	List<KecamatanModel> selectKecamatan(String id);
	KotaModel selectKotaID(String id);
}
