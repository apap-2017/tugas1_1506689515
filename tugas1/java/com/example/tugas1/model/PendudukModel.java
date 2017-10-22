package com.example.tugas1.model;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PendudukModel {
	private String id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private String jenis_kelamin;
	private String is_wni;
	private String id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private String is_wafat;
	
	public String getGender() {
		if (this.jenis_kelamin.equals("1")) {
			return "Perempuan";
		} else {
			return "Laki-laki";
		}
	}
	
}


