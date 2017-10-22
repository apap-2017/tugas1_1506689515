package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nomor_kk);
	KeluargaModel selectKeluargaID(String id);
	List<KeluargaModel> selectWithNKK(String nkkdepan);
	void addKeluarga(KeluargaModel keluarga);
	void ubahKeluarga (KeluargaModel keluarga);
	List<PendudukModel> selectAnggota (String id);
	List<PendudukModel> selectAnggotaHidup (String id);
    


}
