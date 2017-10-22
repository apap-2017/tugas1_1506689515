package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.PendudukModel;

public interface KelurahanService {
	KelurahanModel selectKelurahan(String kode_kelurahan);
	KelurahanModel selectKelurahanID(String id);
	List<KeluargaModel> selectKeluarga(String id);
	List<PendudukModel> selectPendudukTermuda(String idparam);
	List<PendudukModel> selectPendudukTertua(String idparam);
}
