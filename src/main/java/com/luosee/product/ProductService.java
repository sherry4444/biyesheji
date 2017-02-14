package com.luosee.product;

import com.luosee.oss.UploadImage;
import com.luosee.po.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by server1 on 2016/11/26.
 */
@Service
public class ProductService {
    @Resource(name = "productDao")
    private ProductDao productDao;

    public List<Product> queryProduct(Page page)
    {
        return productDao.queryProduct(page);
    }

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public BigInteger queryLastProductId() {
        return productDao.queryLastProductId();
    }

    public void addProductStyle(List<ProductStyle> productStyleList) {
        productDao.addProductStyle(productStyleList);
    }


    public void addProductTag(ProductTagmap productTagmap) {
        productDao.addProductTag(productTagmap);
    }

    public Integer queryProductCount(Page page)
    {
        return  productDao.queryProductCount(page);
    }

    public void deleteProduct(ProductItem productItem,String datasource)
    {
        if(productItem.getProductIds()!=null && productItem.getProductIds().size()!=0)
        {
            UploadImage uploadImage=new UploadImage();

            List<Product> products=productDao.queryIntroduceImg(productItem.getProductIds());
            for (Product product:products)
            {
                if(!product.getIntroduceImg().equals(""))
                {
                    String[] introduceImgs=product.getIntroduceImg().split(",");
                    for (String img:introduceImgs)
                    {
                        if(img != "")
                        {
                            uploadImage.deleteFile(datasource,img);
                        }
                    }
                }
            }

            List<ProductStyle> productStyles=productDao.queryNormalPic(productItem.getProductIds());

            for (ProductStyle style:productStyles)
            {
                uploadImage.deleteFile(datasource,style.getNormalPic());
            }

            productDao.deleteProduct(productItem.getProductIds());
        }
    }

    public Product queryProductInfoByName(Map<String,Object> infoMap)
    {
        return productDao.queryProductInfoByName(infoMap);
    }

    public List<ProductStyle> queryProductStyleByName(Map<String,Object> infoMap)
    {
        return  productDao.queryProductStyleByName(infoMap);
    }

    public void updateProductInfo(Map<String,Object> infoMap)
    {
        productDao.updateProductInfo(infoMap);
    }

    public void deleteProductStyle(List<BigInteger> styleIds)
    {
        productDao.deleteProductStyle(styleIds);
    }

    public void updateProductStyle(List<ProductStyle> productStyleList)
    {
        productDao.updateProductStyle(productStyleList);
    }

    public List<Product> queryIntroduceImg(List<Long> productIds)
    {
        return productDao.queryIntroduceImg(productIds);
    }

    public List<ProductStyle> queryNormalPic(List<Long> productIds)
    {
        return productDao.queryNormalPic(productIds);
    }
}
