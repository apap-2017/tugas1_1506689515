package com.example.tugas1.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KecamatanService;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.KelurahanService;
import com.example.tugas1.service.KotaService;
import com.example.tugas1.service.PendudukService;

@Controller
public class KeluargaController {

	 	@Autowired
	    KeluargaService keluargaDAO;

	 	@Autowired
	 	PendudukService pendudukDAO;

	 	@Autowired
	 	KelurahanService kelurahanDAO;
	 	
	 	@Autowired
	 	KecamatanService kecamatanDAO;
	 	
	 	@Autowired
	 	KotaService kotaDAO;

	 	
	 	// FITUR 2
	 	
	 	
	    @RequestMapping("/keluarga")
	    public String lihat2kel (Model model,
	    		@RequestParam(value="nkk", required = false) String nkk)
	    {
	    	 if (nkk==null) {
	    		 model.addAttribute("errorMessage", "NKK Tidak Bisa Kosong");
		    	return "error/404";
	    	 }
	    	 
	    	 KeluargaModel keluarga = keluargaDAO.selectKeluarga (nkk);

	         if (keluarga != null) {
	        	 
	        	 List<PendudukModel> anggota = keluargaDAO.selectAnggota(keluarga.getId());
	        	 
	        	 KelurahanModel kelurahan = kelurahanDAO.selectKelurahanID(keluarga.getId_kelurahan());
	        	 KecamatanModel kecamatan = kecamatanDAO.selectKecamatanID(kelurahan.getId_kecamatan());
	        	 KotaModel kota = kotaDAO.selectKotaID(kecamatan.getId_kota());
	        	 
	        	 model.addAttribute("anggota",anggota);
	        	 model.addAttribute("keluarga",keluarga);
	        	 model.addAttribute("kelurahan",kelurahan);
	        	 model.addAttribute("kecamatan", kecamatan);
	        	 model.addAttribute("kota", kota);
	             return "view-kel";
	             
	         } else {
	        	 model.addAttribute("errorMessage", "NKK " + nkk + " Tidak Ditemukan");
		    	 return "error/404";
	         }
   
	    }    

	    // FITUR 4
	
	    @RequestMapping("/keluarga/tambah")
	    public String addKeluarga (Model model)
	    {
	    	KeluargaModel keluarga = new KeluargaModel();
	    	model.addAttribute("keluarga", keluarga);
	    	return "form-add-keluarga";
	    	
	    } 
	    
	    @RequestMapping(value = "/keluarga/tambah",method = RequestMethod.POST)
	    public String addKeluargaSubmit (Model model,
	    		@ModelAttribute KeluargaModel keluarga)
	    {
	    	
	    	if(keluarga==null) {
	    		model.addAttribute("errorMessage", "Data Keluarga Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	    	
	    	System.out.println(keluarga.toString());
	    	KelurahanModel kelurahan = kelurahanDAO.selectKelurahanID(keluarga.getId_kelurahan());
	    	System.out.println(kelurahan.toString());
	    	
	    	String nkk = "";
	    	
	    	//6 digit pertama
	    	
	    	nkk += kelurahan.getKode_kelurahan().substring(0, 6);
	    	System.out.println("pertama " + nkk);
	    	
	    	//6 digit tanggal
	    	LocalDate date = LocalDate.now();
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yy");
	    	String[] dates = date.format(formatter).split(" ");
	    	
	    	nkk = nkk + dates[0] + dates[1] + dates[2];
	    	
	    	System.out.println("kedua " + nkk);
	    	
	    	//4 digit urutan
	    	//di % di service
	    	List<KeluargaModel> result = keluargaDAO.selectWithNKK(nkk);
	    	
	    	
	    	if (result.isEmpty()) {
    			//nomor urut 1
    			nkk += "0001";
    		} else {
    			String urutan = "" + (result.size() + 1);
    			String nol = "";
    			if(urutan.length() < 4) {
    				int sisa = 4 - urutan.length();
    				for (int i = 0; i < sisa; i++) {
    					nol += "" + 0;
    				}
    			}
    			
    			System.out.println(urutan);
    			//nomor urut sesuai yg ada
    			nkk = nkk + nol + urutan;
    		}
	    	
	    
    		
	    	keluarga.setNomor_kk(nkk);
	    	
	    	model.addAttribute("keluarga", keluarga);
	    	keluargaDAO.addKeluarga(keluarga);
	    	
	    	return "success-add-keluarga";
	    }
	    
	    // FITUR 6
	    
	    @RequestMapping("/keluarga/ubah/{nkk}")
	    public String ubahKeluarga (Model model, @PathVariable(value = "nkk") String nkk)
	    {
	    	
	    	if(nkk=="") {
	    		model.addAttribute("errorMessage", "NKK Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	    	
	    	 KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

	         if (keluarga != null) {
	        	 model.addAttribute ("keluarga", keluarga);
	             return "form-ubah-keluarga";
	         } else {
	        	 model.addAttribute("errorMessage", "NKK " +nkk+" Tidak Ditemukan");
		    	 return "error/404";
	         }
	        
	    }    
	    
	    @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	    public String ubahKeluargaSubmit ( Model model,
	    		@ModelAttribute KeluargaModel keluarga)
	    {
	   
	    	if (keluarga==null) {
	    		model.addAttribute("errorMessage", "Data Keluarga Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	    	
	    	KeluargaModel keluargaLama = keluargaDAO.selectKeluarga(keluarga.getNomor_kk());
	     	String idKeluarga = keluargaLama.getId();
	    	String nkkUpdated = keluargaLama.getNomor_kk();
	    	
       	 	List<PendudukModel> anggotaKeluarga = keluargaDAO.selectAnggota(idKeluarga);
       	 	
       	 	
	    	boolean isChanged = false;
       	 	
	    	//kalau ada yg ganti
	    	if(!keluarga.getAlamat().equals(keluargaLama.getAlamat())  ||  !keluarga.getIs_tidak_berlaku().equals(keluargaLama.getIs_tidak_berlaku())) {
	    		isChanged = true;
	    		
	    	}
	    	
	    	if (!keluarga.getRT().equals(keluargaLama.getRT())) {
	    		isChanged = true;
	    		
	    		String nol = "";
    			if(keluarga.getRT().length() < 3) {
    				int sisa = 3 - keluarga.getRT().length();
    				for (int jj = 0; jj < sisa; jj++) {
    					nol += "" + 0;
    				}
    				keluarga.setRT(nol + keluarga.getRT());
    			}
    			
	    		
	    	} 
	    	
	    	if (!keluarga.getRW().equals(keluargaLama.getRW()) ) {
	    		isChanged = true;
	    		
	    		String nol = "";
    			if(keluarga.getRW().length() < 3) {
    				int sisa = 3 - keluarga.getRW().length();
    				for (int jj = 0; jj < sisa; jj++) {
    					nol += "" + 0;
    				}
    				keluarga.setRW(nol + keluarga.getRW());
    			}
	    		
	    	}
	    	
	    	//kalau kelurahan ganti
	    	if(!keluarga.getId_kelurahan().equals(keluargaLama.getId_kelurahan())) {
	    		isChanged = true;
	    		
	    		KelurahanModel kelurahanBaru = kelurahanDAO.selectKelurahanID(keluarga.getId_kelurahan());
		    	
	    
	    		nkkUpdated = kelurahanBaru.getKode_kelurahan().substring(0, 6) + nkkUpdated.substring(6);
	    		
	    		for(int i = 0; i < anggotaKeluarga.size(); i++) {
		    		PendudukModel anggota = anggotaKeluarga.get(i);
		    		
		    		String nikLokasi = kelurahanDAO.selectKelurahanID(keluarga.getId_kelurahan()).getKode_kelurahan().substring(0, 6);
		    		
		    		String nikAnggota = nikLokasi+anggota.getNik().substring(6);
		    		
		    		anggota.setNik(nikAnggota);
		    		
		    		//cek urutan
		    		List<PendudukModel> listNIKFound = pendudukDAO.selectWithNIK(nikAnggota.substring(0, 12) + "%");
		    		String urutan = "";
		    		if (listNIKFound.isEmpty()) {
		    			urutan = "0001"	;
		    		} else {
		    			String urutanNik = "" + (listNIKFound.size() + 1);
		    			String nol = "";
		    			if(urutanNik.length() < 4) {
		    				int sisa = 4 - urutanNik.length();
		    				for (int ii = 0; ii < sisa; ii++) {
		    					nol += "" + 0;
		    				}
		    			}
		    			urutan = nol + urutanNik;
		    		}
		    		nikAnggota = nikAnggota.substring(0,12) + urutan;
		    		anggota.setNik(nikAnggota);
		    		
		    		pendudukDAO.ubahPenduduk(anggota);
		    		
		    	}
	    	}
	    	
	    	//kalau ada sesuatu yang ganti, tanggalnya ganti
	    	if(isChanged) {
	    		
	    		LocalDate date = LocalDate.now();
		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yy");
		    	String[] dates = date.format(formatter).split(" ");
		    	String tglUpdate = dates[0] + dates[1] + dates[2];
		    	nkkUpdated = nkkUpdated.substring(0, 6) + tglUpdate + nkkUpdated.substring(12);
		    	
		    	List<KeluargaModel> nkkSama = keluargaDAO.selectWithNKK(nkkUpdated.substring(0, 12));
		    	
		    	if (nkkSama.isEmpty()) {
	    			
		    		nkkUpdated = nkkUpdated.substring(0,12) + "0001";
		    	
	    		} else {
	    			
	    			String urutanKel = "" + (nkkSama.size() + 1);
	    			String nol = "";
	    			if(urutanKel.length() < 4) {
	    				int sisa = 4 - urutanKel.length();
	    				for (int j = 0; j < sisa; j++) {
	    					nol += "" + 0;
	    				}
	    			}
	    			
	    			nkkUpdated = nkkUpdated.substring(0,12) + nol + urutanKel;
	    		}
	    	}
	    	
	    	keluarga.setId(idKeluarga);
	    	keluarga.setNomor_kk(nkkUpdated);
	    	
	    	keluargaDAO.ubahKeluarga(keluarga);
	    	
	    	model.addAttribute("nkkLama", keluargaLama.getNomor_kk());
	    	return "success-ubah-keluarga";
	    }
	    
	
}
