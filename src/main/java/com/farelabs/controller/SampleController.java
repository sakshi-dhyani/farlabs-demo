package com.farelabs.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.farelabs.dto.Response;
import com.farelabs.service.SampleService;

@RestController
public class SampleController {
	
	@Autowired private SampleService sampleService;

	@GetMapping("/hello")
	public String hello(){
		return "testing success";
	}
	
	@PostMapping("/bd/addSampleDetails")
	public ResponseEntity<Response>addSampleDetails(@RequestParam (value ="name" , required=true )String name,
			@RequestParam (value ="companyName" , required=true )String companyName,
			@RequestParam (value ="companyMobile" , required=true )String companyMobile,
			@RequestParam (value ="companyUserName" , required=true )String companyUserName,
			@RequestParam (value ="pickUpAddress" , required=true )String pickUpAddress,
			@RequestParam (value ="deliveryAddress" , required=true )String deliveryAddress,
			@RequestParam (value ="pickupDate" , required=true )String pickupDate,
			@RequestParam (value ="deliveryDate" , required=true )String deliveryDate,
			@RequestParam (value ="entryNumber" , required=true )String entryNumber,
			@RequestParam (value ="serialNumber" , required=true )String serialNumber,
			@RequestParam (value ="quantity" , required=true )String quantity
//			@RequestParam (value ="recipt" , required=true )MultipartFile recipt
			){
		return sampleService.addSampleDetails(name,companyName,companyMobile,companyUserName,pickUpAddress,deliveryAddress,pickupDate,
				deliveryDate,entryNumber,serialNumber,quantity);
	}
	//edit samples
	//get all samples
	@PutMapping("/edit/samples")
	public ResponseEntity<Response>editSample(@RequestParam (value ="companyName" , required=false )String companyName,
			@RequestParam (value ="date" , required=false )String date,
			@RequestParam (value ="companyUserName" , required=false )String companyUserName,
			@RequestParam (value ="name" , required=false )String name,
			@RequestParam (value ="pickUpAddress" , required=false )String pickUpAddress,
			@RequestParam (value ="deliveryAddress" , required=false )String deliveryAddress,
			@RequestParam (value ="sampleId" , required=false )String sampleId){
		return sampleService.editSample(companyName,date,companyUserName,name
				,pickUpAddress,deliveryAddress,sampleId);
	}
	
	//delete sample
	@DeleteMapping("/delete/sample/{sampleId}")
	public ResponseEntity<Response>deleteSample(@PathVariable ("sampleId") int sampleId){
		
		return sampleService.deleteSample(sampleId);
	}
	@GetMapping("/allSamples")
	public ResponseEntity<Response>allSamples(){
		return sampleService.allSamples();
	}
	
	//get sample by id
	@GetMapping("/get/sample/{sampleId}")
	public ResponseEntity<Response>getSample(@PathVariable ("sampleId") int sampleId){
		
		return sampleService.getSample(sampleId);
	}
//	------------------------------------------------------------
	
	//get list of roles
		@GetMapping("/allRoles")
		public ResponseEntity<Response>allRoles(){
			return sampleService.allRoles();
		}
	//get list of sample incharge
	
	@GetMapping("/Incharges/{inchargeID}")
	public ResponseEntity<Response>allIncharges(@PathVariable ("inchargeID")int inchargeID){
		return sampleService.allIncharges(inchargeID);
	}
	

	@GetMapping("/executives/{executiveID}")
	public ResponseEntity<Response>executives(@PathVariable ("executiveID")int executiveID){
		return sampleService.executives(executiveID);
	}
	@GetMapping("/allBd/{bdId}")
	public ResponseEntity<Response>allBd(@PathVariable ("bdId")int bdId){
		return sampleService.allBd(bdId);
	}
	
	//CHECKPONTS---------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//api for ssf allotment 
	
	@GetMapping("/check/ssfAllotment/{sampleId}")
	public ResponseEntity<Response>ssfAllotment(@PathVariable ("sampleId")int sampleId){
		return sampleService.ssfAllotment(sampleId);

	}
	@GetMapping("/check/equipmentPickup/{sampleId}")
	public ResponseEntity<Response>equipmentPickup(@PathVariable ("sampleId")int sampleId){
		return sampleService.equipmentPickup(sampleId);

	}
	@GetMapping("/check/leftForDelivery/{sampleId}")
	public ResponseEntity<Response>leftForDelivery(@PathVariable ("sampleId")int sampleId){
		return sampleService.leftForDelivery(sampleId);

	}
	@GetMapping("/check/samplePickup/{sampleId}")
	public ResponseEntity<Response>samplePickup(@PathVariable ("sampleId")int sampleId){
		return sampleService.samplePickup(sampleId);

	}
	@GetMapping("/check/ssf/{sampleId}")
	public ResponseEntity<Response>ssf(@PathVariable ("sampleId")int sampleId){
		return sampleService.ssf(sampleId);

	}
	
	//get checkpoints

	@GetMapping("/getCheckPoints/{sampleId}")
	public ResponseEntity<Response>getCheckPoints(@PathVariable ("sampleId")int sampleId){
		return sampleService.getCheckPoints(sampleId);
	}
	//-------------------------------------------------------------------------------------------------------------------------

	///alocating samples 
	@PostMapping("/incharge/allocateSamples")
	public ResponseEntity<Response>allocateSamples(@RequestParam (value ="sampleId" , required=false )String sampleId,
			@RequestParam (value ="executiveId" , required=false )String executiveId){
		return sampleService.allocateSamples(sampleId,executiveId);
	}
	
	
	//edit allocation sampling
	@PutMapping("/edit/sampleAllocation")
	public ResponseEntity<Response>editAllocations(@RequestParam (value ="executiveId" , required=false )String executiveId,
			@RequestParam (value ="sampleId" , required=false )String sampleId){


		
		return sampleService.editAllocations(sampleId,executiveId);
	}
	
	//get list of which executive get which sample
	
	@GetMapping("/getSamplesAssigned/{executiveId}")
	public ResponseEntity<Response>samplesAssigned(@PathVariable ("executiveId") int executiveId){
		
		return sampleService.samplesAssigned(executiveId);
	}
	
	//get sample status
	@GetMapping("/bd/getSampleStatus/{sampleId}")
    public ResponseEntity<Response>getSampleStatus(@PathVariable ("sampleId") int sampleId){
		
		return sampleService.getSampleStatus(sampleId);
	}
	
	//sample collection
	
	@PostMapping("/executive/storeSampleDetails")
	public ResponseEntity<Response>storeSampleDetails(@RequestParam (value ="sampleId" , required=false )String sampleId,
			@RequestParam (value ="quantity" , required=false )int quantity,
			@RequestParam (value ="recieverName" , required=false )String recieverName,

			@RequestParam (value ="courierDetails" , required=false )String courierDetails,

			@RequestParam (value ="ssfPicture" , required=false )MultipartFile ssfPicture,

			@RequestParam (value ="samplePicture" , required=false )MultipartFile samplePicture){
		
		return sampleService.storeSampleDetails(sampleId,quantity,recieverName,courierDetails,ssfPicture,samplePicture);

	}
	@GetMapping("/downloadSampleImage/{imageName}")
	public ResponseEntity<Resource> downloadsampleImage(@PathVariable String imageName) throws IOException {

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		File file = new File("C:\\Farelabs\\Sample\\images\\" + imageName);

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new ByteArrayResource(Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
	}
	@GetMapping("/downloadSsfImage/{imageName}")
	public ResponseEntity<Resource> downloadSsfImage(@PathVariable String imageName) throws IOException {

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		File file = new File("C:\\Farelabs\\Sample\\SSf\\images\\" + imageName);

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new ByteArrayResource(Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
	}
	
	//sample history
	
	@GetMapping("/myHistory")
	public ResponseEntity<Response>myHistory(@RequestParam (value ="executiveId" , required=true )String executiveId,
			@RequestParam (value ="date" , required=false )String date
			){
		
		return sampleService.myHistory(executiveId,date);
	}
	
	@GetMapping("/myHistory/date")
	public ResponseEntity<Response>myHistoryDate(@RequestParam (value ="executiveId" , required=true )String executiveId,
			@RequestParam (value ="date" , required=false )String date
			){
		
		return sampleService.myHistoryDate(executiveId,date);
	}
	
}
