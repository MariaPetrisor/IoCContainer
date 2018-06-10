package assembler;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class FileSystemXmlApplicationContext extends ApplicationContext {
	
	private Map<String, Object> objectMap = new HashMap<String, Object>();
	
	public FileSystemXmlApplicationContext(String file) {
		try {
			File inputFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("bean");
			LinkedList<Bean> beanList = new LinkedList<Bean>();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Bean bean = new Bean();
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
//					System.out.println(eElement.getAttribute("id"));
					bean.setId(eElement.getAttribute("id"));
//					System.out.println(eElement.getAttribute("class"));
					bean.setClassName(eElement.getAttribute("class"));

					NodeList childNodes = eElement.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node child = childNodes.item(j);
						if(childNodes.item(j).getNodeName().equals("property")){
							Element childElement = (Element) child;
//							System.out.println(childElement.getAttribute("name"));

//							System.out.println(getString("value", childElement));
							if(getString("value", childElement) != null) {
								bean.addValueMap(childElement.getAttribute("name"), getString("value", childElement));
							}
							if(getString("ref", childElement) != null) {
								bean.addRefMap(childElement.getAttribute("name"), getString("ref", childElement)); 
							}

						}
					}
				}
//				System.out.println(bean);
				beanList.add(bean);
			}
		
		while(!areAllBeansResolved(beanList, objectMap)) {
			for(Bean bean: beanList) {
	//			System.out.println(bean);
				if((bean.getRefMap().size() == 0 || checkRefs(bean, objectMap)) && !beanInObjMap(bean, objectMap)) {
					
					Class<?> clazz = Class.forName(bean.getClassName());
					for (Map.Entry entry : bean.getValueMap().entrySet()) {
						String name = entry.getKey().toString();
						StringBuilder method = new StringBuilder(name);
						method.setCharAt(0, Character.toUpperCase(method.charAt(0)));
						
						
					    String methodName = "set" + method;
	//				    System.out.println(methodName);
					    
					    Method[] allMethods = clazz.getDeclaredMethods();
					    for (Method m : allMethods) {
					    	if (m.getName().equals(methodName)) {
					    		Class<?>[] pType  = m.getParameterTypes();
					    		
					    		Object o = clazz.newInstance();
							    
					    		//Invoke the setter
							    m.invoke(o, entry.getValue());
							    
							    System.out.println("Object created" + o);
							    
							    objectMap.put(bean.getId(), o);
					    	}
					    }				    
					    
					}
					
					for (Map.Entry entry : bean.getRefMap().entrySet()) {
						String name = entry.getKey().toString();
						StringBuilder method = new StringBuilder(name);
						method.setCharAt(0, Character.toUpperCase(method.charAt(0)));
						
						
					    String methodName = "set" + method;
					    
					    
					    Method[] allMethods = clazz.getDeclaredMethods();
					    for (Method m : allMethods) {
					    	if (m.getName().equals(methodName)) {
					    		Class<?>[] pType  = m.getParameterTypes();
					    		
					    		Object o = clazz.newInstance();
					    		
					    		Object value = objectMap.get(entry.getValue());
					    		
					    		if(value != null) {
					    			m.invoke(o, value);
					    		}
							    
//							    m.invoke(o, entry.getValue());
							    
							    System.out.println("Object created" + o);
							    
							    objectMap.put(bean.getId(), o);
							    
					    	}
					    }
				}
				}
//				System.out.println("Has refs:" + checkRefs(beanList.get(0), objectMap));
			}
		}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	}

	
	public String getString(String tagName, Element element) {
		NodeList list = element.getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			if(list.item(0).getNodeName().equals("ref")) {
				Element ref = (Element) list.item(0);
				return ref.getAttribute("local");
			}


			NodeList subList = list.item(0).getChildNodes();
			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}

		return null;
	}
	
	public boolean checkRefs(Bean bean, Map<String, Object> objectMap) {
		for (Map.Entry entry : bean.getRefMap().entrySet()) {
		    Object value = objectMap.get(entry.getValue());
		    if(value == null) {
		    	return false;
		    }
		}
		return true;
	}
	
	public boolean beanInObjMap(Bean bean, Map<String, Object> objectMap) {
	    Object value = objectMap.get(bean.getId());
	    if(value == null) {
	    	return false;
	    }
	    return true;
	}
	
	public boolean areAllBeansResolved(LinkedList<Bean> beanList, Map<String, Object> objectMap) {
		for(Bean bean: beanList) {
			Object value = objectMap.get(bean.getId());
//			System.out.println(value);
		    if(value == null) {
		    	return false;
		    }
		}
		return true;
	}

	@Override
	public Object getBean(String cls) {
		return objectMap.get(cls);
	}
}
