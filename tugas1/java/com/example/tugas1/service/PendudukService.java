package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.tugas1.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	List<PendudukModel> selectWithNIK(String nikdepan);
	void addPenduduk(PendudukModel penduduk);
	void ubahPenduduk (PendudukModel penduduk);
	List<PendudukModel> selectPendudukKelurahan(String idparam);

}
