package tw.eeit117.wematch.product.model;

import java.util.List;

public interface IProductBeanDAO {
	public List<ProductBean> selectAll();

	public void insert(ProductBean productBean);

	public String deleteById(Integer productId);

	public ProductBean findById(Integer productId);

	public void update(ProductBean productBean);

	public List<ProductBean> findByKeyword(String keyword);
}
