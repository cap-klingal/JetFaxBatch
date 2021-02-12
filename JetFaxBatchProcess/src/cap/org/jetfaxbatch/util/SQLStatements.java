package cap.org.jetfaxbatch.util;


public class SQLStatements {
	
	public final String PRODUCTS_DROPDOWN =
    "SELECT ib.segment1 product, ib.description , ib.item_type , ib.attribute1 catalog_page, 'Unspecified' status "+ 
	"FROM xxcap.xxcap_item_list_price_mv pl1, "+
	"qp.qp_list_headers_tl plh, "+
	"inv.mtl_system_items_b ib "+
	"WHERE  pl1.list_header_id = plh.list_header_id "+ 
	"AND ib.inventory_item_id  = pl1.product_attr_value "+
	"AND plh.name = ? || ' CAP Standard' "+
	"AND ib.organization_id = 81 "+
	"AND ib.item_type = ? "+
	"AND INSTR(ib.segment1, '-') = 0  "+
	"AND EXISTS( SELECT 1 "+
	"FROM xxcap.xxcap_item_list_price_mv pl2, "+
	"qp.qp_list_headers_tl plh2, "+
    "inv.mtl_system_items_b ib2 "+
	"WHERE  pl2.list_header_id = plh2.list_header_id "+
	   "AND ib2.inventory_item_id  = pl2.product_attr_value "+
	   "AND plh2.name = (? - 1) || ' CAP Standard' "+
	   "AND ib2.organization_id = 81 "+
	   "AND ib2.segment1 = ib.segment1) "+
	"UNION "+
	"SELECT ib1.segment1 product, ib1.description product_name, ib1.item_type product_type, ib1.attribute1 catalog_page, 'New' status "+
	"FROM xxcap.xxcap_item_list_price_mv pl1, "+
	 "    qp.qp_list_headers_tl plh1, "+
	  "   inv.mtl_system_items_b ib1 "+
	"WHERE  pl1.list_header_id = plh1.list_header_id "+
	 " AND ib1.inventory_item_id  = pl1.product_attr_value "+
	  "AND plh1.name = ? || ' CAP Standard' "+
	  "AND ib1.organization_id = 81 "+
	  "AND ib1.item_type = ? "+
	  "AND INSTR(ib1.segment1, '-') = 0 "+
	  "AND NOT EXISTS( SELECT 1 "+
	   "   FROM xxcap.xxcap_item_list_price_mv pl2, "+
	    "  qp.qp_list_headers_tl plh2, "+
	     " inv.mtl_system_items_b ib2 "+
	      "WHERE  pl2.list_header_id = plh2.list_header_id "+
	    "AND ib2.inventory_item_id  = pl2.product_attr_value "+
	    "AND plh2.name = (? - 1) || ' CAP Standard' "+
	    "AND ib2.organization_id = 81 "+
	    "AND ib2.segment1 = ib1.segment1) "+
	"UNION "+
	"SELECT ib1.segment1 product, ib1.description product_name, ib1.item_type product_type, ib1.attribute1 catalog_page, 'Discontinued' status "+
	"FROM xxcap.xxcap_item_list_price_mv pl1, "+
	 "    qp.qp_list_headers_tl plh1, "+
	   "  inv.mtl_system_items_b ib1 "+
	"WHERE  pl1.list_header_id = plh1.list_header_id "+
	 " AND ib1.inventory_item_id  = pl1.product_attr_value "+
	 " AND plh1.name = (? - 1) || ' CAP Standard' "+
	  "AND ib1.organization_id = 81 "+
	  "AND ib1.item_type = ? "+
	 " AND INSTR(ib1.segment1, '-') = 0 "+
	 " AND NOT EXISTS( SELECT 1 "+
	     " FROM xxcap.xxcap_item_list_price_mv pl2, "+
	     " qp.qp_list_headers_tl plh2, "+
	     " inv.mtl_system_items_b ib2 "+
	     " WHERE  pl2.list_header_id = plh2.list_header_id  "+
	   " AND ib2.inventory_item_id  = pl2.product_attr_value "+
	   " AND plh2.name = ? || ' CAP Standard' "+
	    "AND ib2.organization_id = 81 "+
	    "AND ib2.segment1 = ib1.segment1) ";
	
	public final String jetFaxFlagUpdate = "update apps.pa_proj_elements set attribute3 = 'Y' where project_id= ? and name= ? ";
	
	public final String getProductIds = "select project_id from apps.pa_proj_elements where name = ?";
}