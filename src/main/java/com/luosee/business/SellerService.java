package com.luosee.business;

import com.luosee.oss.Constant;
import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import com.luosee.product.ProductDao;
import com.luosee.product.ProductItem;
import com.luosee.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by server1 on 2016/11/21.
 */
@Service
public class SellerService {

    @Resource(name = "sellerDao")
    private  SellerDao sellerDao;

    @Resource(name = "productDao")
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    public List<Seller> findSellerByName(Page page){
        return  sellerDao.findSellerByName(page);
    }

    public Integer sellerCount(Page page)
    {
        return sellerDao.sellerCount(page);
    }
    public void addSeller(Seller seller)
    {
        sellerDao.addSeller(seller);
    }

    public void deleteSeller(int id,String datasource)
    {

        Seller seller=sellerDao.queryBrand(id);

        if(seller.getSellerLogo() != null)
        {
            UploadImage image=new UploadImage();
            image.deleteFile(datasource,seller.getSellerLogo() );
        }
        if(seller.getPowerAttorney() != null)
        {
            UploadImage image=new UploadImage();
            image.deleteFile(datasource,seller.getPowerAttorney() );
        }
        if(seller.getTrademark() != null)
        {
            UploadImage image=new UploadImage();
            image.deleteFile(datasource,seller.getTrademark() );
        }

        sellerDao.deleteSeller(id);

        ProductItem productItem=new ProductItem();
        productItem.setProductIds(productDao.query_seller_product(id));
        productService.deleteProduct(productItem,datasource);
    }


    public void updateBrand(Seller seller, String datasource, MultipartFile logo)
    {
        UploadImage image=new UploadImage();


        Seller imgUrl=sellerDao.queryBrand(seller.getSellerId().intValue());

        if(!logo.getOriginalFilename().equals(""))
        {
            seller.setSellerLogo(image.uploadFile(logo,datasource,seller.getSellerId(),Constant.OSS_BUSINESS_IMAGES+"/"));
            image.deleteFile(datasource, Constant.OSS_BUSINESS_IMAGES+"/"+imgUrl.getSellerLogo());
        }
        else
        {
            seller.setSellerLogo(imgUrl.getSellerLogo());
        }

        sellerDao.updateBrand(seller);
    }
}
