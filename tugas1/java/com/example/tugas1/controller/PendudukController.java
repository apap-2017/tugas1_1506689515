package com.example.tugas1.controller;

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
public class PendudukController {

	 	@Autowired
	    PendudukService pendudukDAO;
	 	
	 	@Autowired
	 	KeluargaService keluargaDAO;
	 	
	 	@Autowired
	 	KelurahanService kelurahanDAO;

	 	@Autowired
	 	KecamatanService kecamatanDAO;
	 	
	 	@Autowired
	 	KotaService kotaDAO;
	 	
	 	// FITUR 1
	 	
	 	@RequestMapping("/") 
	 	public String lihat ()
	 	{
	 		return "form-view";
	 	}
	 
	 	
	    @RequestMapping("/penduduk")
	    public String lihat2 (Model model,
	    		@RequestParam(value="nik", required = false) String nik)
	    {
	    	
	    	if(nik == null) {
	    		model.addAttribute("errorMessage", "NIK Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	    	
	    	 PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);

	         if (penduduk != null) {
	        	 KeluargaModel keluarga = keluargaDAO.selectKeluargaID(penduduk.getId_keluarga());
	        	 KelurahanModel kelurahan = kelurahanDAO.selectKelurahanID(keluarga.getId_kelurahan());
	        	 KecamatanModel kecamatan = kecamatanDAO.selectKecamatanID(kelurahan.getId());
	        	 KotaModel kota = kotaDAO.selectKotaID(kecamatan.getId_kota());
	        	 
	        	 model.addAttribute("penduduk",penduduk);
	        	 model.addAttribute("rtrw", keluarga.getRT() + "/" + keluarga.getRW());
	        	 model.addAttribute("kelurahan", kelurahan);
	        	 model.addAttribute("kecamatan", kecamatan);
	        	 model.addAttribute("kota",kota);
	        	 model.addAttribute("keluarga", keluarga);
	             return "view";
	         } else {
	        	 model.addAttribute("errorMessage", "NIK " + nik + " Tidak Ditemukan");
		    	 return "error/404";
	         }
	        
	    }   
	    
	    // FITUR 3
	    
	    @RequestMapping("/penduduk/tambah")
	    public String addPenduduk (Model model)
	    {
	    	PendudukModel penduduk = new PendudukModel();
	    	model.addAttribute("penduduk", penduduk);
	    	return "form-add-penduduk";
	    	
	    }   
	    

	    @RequestMapping(value = "/penduduk/tambah",method = RequestMethod.POST)
	    public String addPendudukSubmit (Model model,
	    		@ModelAttribute PendudukModel penduduk)
	    {
	    		
	    	if (penduduk==null) {
	    		model.addAttribute("errorMessage", "Data Penduduk Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	    		
	    		KeluargaModel keluarga = keluargaDAO.selectKeluargaID(penduduk.getId_keluarga());
	    		KelurahanModel kelurahan = kelurahanDAO.selectKelurahanID(keluarga.getId_kelurahan());
	    		KecamatanModel kecamatan = kecamatanDAO.selectKecamatanID(kelurahan.getId_kecamatan());
	    		String nik = "";
	    		
	    		//6 digit pertama nik
	    		String lokasi = kecamatan.getKode_kecamatan().substring(0, 6);
	    		
	    		nik += lokasi;
	    		
	    		//6 digit tgl lahir
	    		String[] birthDate = penduduk.getTanggal_lahir().split("-");
	    		String tanggal = birthDate[2];
	    		String bulan = birthDate[1];
	    		String tahun = birthDate[0].substring(2);
	    		
	    		//perempuan = tanggal + 40
	    		if (penduduk.getJenis_kelamin().equals("1")) {
	    			int tanggalPerempuan = Integer.parseInt(tanggal) + 40;
	    			tanggal = "" + tanggalPerempuan;
	    		}
	    		
	    		nik = nik + tanggal + bulan + tahun;
	    		
	    		//4 digit nomor urut
	    		
	    		String cariNik = nik + "%";
	    		List<PendudukModel> listNIKFound = pendudukDAO.selectWithNIK(cariNik);
	    		
	    		if (listNIKFound.isEmpty()) {
	    			
	    			//nomor urut 1
	    			nik += "0001";
	    		} else {
	    			String urutan = "" + (listNIKFound.size() + 1);
	    			String nol = "";
	    			if(urutan.length() < 4) {
	    				int sisa = 4 - urutan.length();
	    				for (int i = 0; i < sisa; i++) {
	    					nol += "" + 0;
	    				}
	    			}
	    			//nomor urut sesuai yg ada
	    			nik = nik + nol + urutan;
	    		}
	    		
	    		penduduk.setNik(nik);
	    		model.addAttribute("penduduk",penduduk);
	    		pendudukDAO.addPenduduk(penduduk);
	  
	   			return "success-add-penduduk";
	    	
	    }  
	    
	    
	    // FITUR 5
	    
	    @RequestMapping("/penduduk/ubah/{nik}")
	    public String ubahPenduduk (Model model, @PathVariable(value = "nik") String nik)
	    {
	    	if (nik == "") {
	    		model.addAttribute("errorMessage", "NIK Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	    	 PendudukModel penduduk = pendudukDAO.selectPenduduk (nik);

	         if (penduduk != null) {
	        	 model.addAttribute ("penduduk", penduduk);
	             return "form-ubah-penduduk";
	         } else {
	        	 model.addAttribute("errorMessage", "NIK " + nik + " Tidak Ditemukan");
		    	 return "error/404";
	         }
	        
	    }    
	    
	    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	    public String ubahPendudukSubmit ( Model model,
	    		@ModelAttribute PendudukModel penduduk)
	    {
	    	//penduduk merepresentasikan penduduk dengan data baru, tp blm diganti ke database
	    	
	    	//benerin nik
	    	PendudukModel pendudukLama = pendudukDAO.selectPenduduk(penduduk.getNik());
	    	String nikUpdated = pendudukLama.getNik();
	    	
	    
	    	//jika mengganti tanggal lahir, atau jenis kelamin
	    	if (pendudukLama.getTanggal_lahir().equals(penduduk.getTanggal_lahir())) {
	    		
	    		//jika mengganti jenis kelamin
		    	if (pendudukLama.getJenis_kelamin().equals(penduduk.getJenis_kelamin())) {
		    		
		    	} else {
		    		
		    		int tanggal = Integer.parseInt(nikUpdated.substring(6, 8));
		    		
		    		if (pendudukLama.getJenis_kelamin().equals("0")) {
		    			//mengubah jadi perempuan
		    			tanggal += 40;
		    			
		    		} else {
		    			//mengubah jadi laki-laki
		    			tanggal = tanggal - 40;
		    		}
		    		
		    		nikUpdated = nikUpdated.substring(0,6) + tanggal + nikUpdated.substring(8);
		    	}
		    	
	    	} else {
	    		String[] birthDate = penduduk.getTanggal_lahir().split("-");
	    		String tanggal = birthDate[2];
	    		String bulan = birthDate[1];
	    		String tahun = birthDate[0].substring(2);
	    		
	    		//perempuan = tanggal + 40
	    		if (penduduk.getJenis_kelamin().equals("1")) {
	    			int tanggalPerempuan = Integer.parseInt(tanggal) + 40;
	    			tanggal = "" + tanggalPerempuan;
	    		}
	    		
	    		String nikLahir = tanggal + bulan + tahun;
	    		nikUpdated = nikUpdated.substring(0, 6) + nikLahir + nikUpdated.substring(12);
	    		
	    	}
	    	
	    	
	    	
	    	//jika mengganti id keluarga
	    	if (pendudukLama.getId_keluarga().equals(penduduk.getId_keluarga())) {
	    		
	    	} else {
	    		//ganti tgl nik lokasi, substring 0,6
	    		KeluargaModel keluargaUpdate = keluargaDAO.selectKeluargaID(penduduk.getId_keluarga());
	    		KelurahanModel kelurahanUpdate = kelurahanDAO.selectKelurahanID(keluargaUpdate.getId_kelurahan());
	    		KecamatanModel kecamatanUpdate = kecamatanDAO.selectKecamatanID(kelurahanUpdate.getId_kecamatan());
	    		
	    		//6 digit pertama updated nik
	    		String lokasi = kecamatanUpdate.getKode_kecamatan().substring(0, 6);
	    		nikUpdated = lokasi + nikUpdated.substring(6);
	    		
	    	}
	    	
	    	
	    	//cek nomor urut nik
	    	
	    	if(nikUpdated.equals(pendudukLama.getNik())) {
	    		
	    	} else {
	    	
	    		String cariNik = nikUpdated.substring(0, 12) + "%";
	    		List<PendudukModel> listNIKFound = pendudukDAO.selectWithNIK(cariNik);
	    		
	    		if (listNIKFound.isEmpty()) {
	    			//nomor urut 1
	    			nikUpdated = nikUpdated.substring(0, 12) + "0001";
	    		} else {
	    			String urutan = "" + (listNIKFound.size() + 1);
	    			String nol = "";
	    			if(urutan.length() < 4) {
	    				int sisa = 4 - urutan.length();
	    				for (int i = 0; i < sisa; i++) {
	    					nol += "" + 0;
	    				}
	    			}
	    			//nomor urut sesuai yg ada
	    			nikUpdated = nikUpdated.substring(0, 12) + nol + urutan;
	    		}
    		
	    	}
	    	
	    	
	    	penduduk.setNik(nikUpdated);
	    	
	    	penduduk.setId(pendudukLama.getId());
	    	
	    	model.addAttribute("nikLama", pendudukLama.getNik());
	    	//update ke database
	    	pendudukDAO.ubahPenduduk (penduduk);
	    	
	        return "success-ubah-penduduk";

	    }    

	    
	    // FITUR 7
	  
	    @RequestMapping("/penduduk/mati")
		public String updateStatusMati (@RequestParam(value = "nik", required = false) String nik, Model model)
		{
	    	
	    	if (nik==null) {
	    		model.addAttribute("errorMessage", "NIK Tidak Bisa Kosong");
	    		return "error/404";
	    	}
	
	    	PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
	    	
	    	
	    	if (penduduk != null) {
	    		
	    		penduduk.setIs_wafat("1");
	    		
	    		pendudukDAO.ubahPenduduk(penduduk);
	    		
	    		List<PendudukModel> keluarga = keluargaDAO.selectAnggotaHidup(penduduk.getId_keluarga());
	    		
	    		System.out.println(keluarga.size());
	    		
	    		if (keluarga.isEmpty()) {
	    			
	    			KeluargaModel keluargaMati = keluargaDAO.selectKeluargaID(penduduk.getId_keluarga());
	    			keluargaMati.setIs_tidak_berlaku("1");
	    			keluargaDAO.ubahKeluarga(keluargaMati);
	    			
	    		}
	    		
	    		model.addAttribute("nik", nik);
	    		return "success-tidak-aktif";
	    		
	    	} else {
	    		model.addAttribute("errorMessage", "NIK " + nik + " Tidak Ditemukan");
	    		return "error/404";
	    	}
	    	
		}
	    
	    
	    // FITUR 8
	    
		@RequestMapping("/penduduk/cari")
	    public String cariDataPenduduk(@RequestParam(value = "kt", required = false) String kt, @RequestParam(value = "kc", required = false) String kc,
	    		@RequestParam(value = "kl", required = false) String kl, Model model)
	    {
			
			
			if(kt == null) {
				model.addAttribute("ktIsFilled", false);
				model.addAttribute("list_kt", kotaDAO.selectKota());
				return "form-cari-penduduk";
				
			} else {
				model.addAttribute("ktIsFilled", true);
				System.out.println("pertama pas kt udh diisi, hrsnya dua kali sih");
				System.out.println("nih kt nya" + kt);
				model.addAttribute("kt", kt);
				model.addAttribute("nama_kt",kotaDAO.selectKotaID(kt).getNama_kota());
				
				if(kc == null) {
					System.out.println("kcnya null harusnya sekali aja");
					
					model.addAttribute("kcIsFilled", false);
					model.addAttribute("list_kc", kotaDAO.selectKecamatan(kt));
					return "form-cari-penduduk";
					
				} else {
					System.out.println("masuk sini kok");
					System.out.println(kecamatanDAO.selectKecamatanID(kc).getNama_kecamatan());
					model.addAttribute("kcIsFilled", true);
					model.addAttribute("kc", kc);
					model.addAttribute("nama_kc", kecamatanDAO.selectKecamatanID(kc).getNama_kecamatan());
					System.out.println(kecamatanDAO.selectKecamatanID(kc).getNama_kecamatan());
					if(kl == null) {
						model.addAttribute("klIsFilled", false);
						model.addAttribute("list_kl", kecamatanDAO.selectKelurahan(kc));
						return "form-cari-penduduk";
						
					} else {
						
						
						List<PendudukModel> pendudukDiKelurahan = pendudukDAO.selectPendudukKelurahan(kl);
						model.addAttribute("kl", kl);
						model.addAttribute("list_penduduk", pendudukDiKelurahan);
						model.addAttribute("nama_kl", kelurahanDAO.selectKelurahanID(kl).getNama_kelurahan());
						
						// BONUS
						
						List<PendudukModel> termuda = kelurahanDAO.selectPendudukTermuda(kl);
						List<PendudukModel> tertua = kelurahanDAO.selectPendudukTertua(kl);
						
						model.addAttribute("termuda", termuda);
						model.addAttribute("tertua", tertua);

						
						return "view-penduduk-kl";
						
					}
					
				}
				
				
			}
			
		
			
	    }
		
	    
}
