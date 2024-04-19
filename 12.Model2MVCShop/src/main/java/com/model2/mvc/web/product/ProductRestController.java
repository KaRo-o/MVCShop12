package com.model2.mvc.web.product;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model2.mvc.common.Image;
import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.Image.ImageService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;



@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("imageServiceImpl")
	private ImageService imageService;

//	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
//	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo) throws Exception {
		
		System.out.println("/product/json/getProduct/ : GET	");
		
		return productService.getProduct(prodNo);
	}
	
	@RequestMapping(value="json/listProduct", method = RequestMethod.POST)
	public Map<String, Object> listProduct( @ModelAttribute("search") Search search, Model model
										,HttpServletRequest request, @RequestBody Search request2) throws Exception {
		
		int currentPage = request2.getCurrentPage();
		
		search.setCurrentPage(currentPage);
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
			
		return map;
	}
	
	@PostMapping(value="json/addProduct")
	public void addProduct(MultipartHttpServletRequest request)throws Exception{
		Product product = new Product();
		Image image = new Image();
		
		String uploadPath = "C:\\Users\\bitcamp\\git\\MVCShop11\\11.Model2MVCShop\\src\\main\\webapp\\images\\uploadFiles\\";
		
		List<MultipartFile> file = request.getFiles("fileName");
		
		UUID fileKey = UUID.randomUUID();
		product.setFileName(fileKey.toString());
		image.setFileKey(fileKey.toString());
		
		for(MultipartFile i : file) {
			if(i!=null) {
			String originalFileName = i.getOriginalFilename(); //파일명 받아오기
			UUID uuid = UUID.randomUUID(); // Unique한 값 생성
			
			System.out.println("originalFileName : "+originalFileName + ", UUID : "+uuid);
			
			String fileName = uuid.toString() + "_" + originalFileName; // Unique 한 값고 기존 파일 명 합쳐서 중복되지 않는 이름으로 저장
			
			image.setFileName(fileName);
			imageService.addImage(image);
			i.transferTo(new File(uploadPath+fileName));  // 파일 업로드할 경로에 지정한 파일명으로 업로드
			}
		}
		

		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		//product.setFileName(fileName);

		if(product.getManuDate().contains("-")) {
			String[] md = product.getManuDate().split("-");
			product.setManuDate(md[0]+md[1]+md[2]);
		}
		System.out.println(product.toString());
		
		productService.addProduct(product);
		request.setAttribute("product", product);
	}
	
	@GetMapping(value="/json/updateProductView/{prodNo}")
	public Map<String,Object> updateProdudctView(@PathVariable("prodNo") int prodNo, Model model) throws Exception{
		
		
		Product product = productService.getProduct(prodNo);
		Map<String, Object> img = imageService.getImages(product.getFileName());
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", img);
		map.put("product", product);
		
		return map;
		
	}
	
	@PostMapping(value="/json/updateProduct")
	public Product updateProduct(MultipartHttpServletRequest request)throws Exception{
		
		Product product = new Product();
		Image image = new Image();
		
		String uploadPath = "C:\\Users\\bitcamp\\git\\MVCShop11\\11.Model2MVCShop\\src\\main\\webapp\\images\\uploadFiles\\";
		
		List<MultipartFile> file = request.getFiles("fileName");
		
		UUID fileKey = UUID.randomUUID();
		product.setFileName(fileKey.toString());
		image.setFileKey(fileKey.toString());
		
		for(MultipartFile i : file) {
			if(i!=null) {
			String originalFileName = i.getOriginalFilename(); //파일명 받아오기
			UUID uuid = UUID.randomUUID(); // Unique한 값 생성
			
			System.out.println("originalFileName : "+originalFileName + ", UUID : "+uuid);
			
			String fileName = uuid.toString() + "_" + originalFileName; // Unique 한 값고 기존 파일 명 합쳐서 중복되지 않는 이름으로 저장
			
			image.setFileName(fileName);
			imageService.addImage(image);
			i.transferTo(new File(uploadPath+fileName));  // 파일 업로드할 경로에 지정한 파일명으로 업로드
			}
		}
		

		
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		//product.setFileName(fileName);

		if(product.getManuDate().contains("-")) {
			String[] md = product.getManuDate().split("-");
			product.setManuDate(md[0]+md[1]+md[2]);
		}
		System.out.println(product.toString());
		
		productService.updateProduct(product);
		request.setAttribute("product", product);
		
		return product;
		
	}
	
	
	

}
