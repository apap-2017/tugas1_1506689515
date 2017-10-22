package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;

public interface KecamatanService {
	KecamatanModel selectKecamatan(String kode_kecamatan);
	KecamatanModel selectKecamatanID(String id);
	List<KelurahanModel> selectKelurahan(String id);

}
