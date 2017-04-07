package com.model.physics.manager;
import java.util.ArrayList;  
import java.util.Collection;
import java.util.Iterator;  
import java.util.Map;
import java.util.Stack;  

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.model.domain.Line;
import com.model.domain.Lru;
import com.model.domain.Node;
import com.model.lru.dao.LruDao;
import com.model.line.dao.LineDao;  
import com.model.repair.manager.RepairManager;

@Component("test")
public class test {  
	@Resource
	private LruDao lruDao;
	@Resource
	private LineDao lineDao;
	@Resource
	private RepairManager repairManager ;
    /* ��ʱ����·���ڵ��ջ */  
    public static Stack<Node> stack = new Stack<Node>();  
    /* �洢·���ļ��� */  
    public static ArrayList<Object[]> sers = new ArrayList<Object[]>();  
  
    /* �жϽڵ��Ƿ���ջ�� */  
    public static boolean isNodeInStack(Node node)  
    {  
        Iterator<Node> it = stack.iterator();  
        while (it.hasNext()) {  
            Node node1 = (Node) it.next();  
            if (node == node1)  
                return true;  
        }  
        return false;  
    }  
  
    /* ��ʱջ�еĽڵ����һ������·����ת������ӡ��� */  
    public void showAndSavePath() throws Exception  
    {  
        Object[] o = stack.toArray();
        String result = "";  
        for (int i = 0; i < o.length; i++) {  
            Node nNode = (Node) o[i];  
             
            if(i < (o.length - 1)){
                System.out.print(nNode.getLru() + "->");
            	result += nNode.getLru() + "->";
            	}
            else{
                System.out.print(nNode.getLru());  
                result += nNode.getLru();
            }
        }  
        sers.add(o); /* ת�� */  
        System.out.println("\n");
        repairManager.save(result);
    }  
  
    /* 
     * Ѱ��·���ķ���  
     * cNode: ��ǰ����ʼ�ڵ�currentNode 
     * pNode: ��ǰ��ʼ�ڵ����һ�ڵ�previousNode 
     * sNode: �������ʼ�ڵ�startNode 
     * eNode: �յ�endNode 
     */  
    public boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode) throws Exception {  
        Node nNode = null;  
        /* ������������ж�˵�����ֻ�·��������˳�Ÿ�·������Ѱ·������false */  
        if (cNode != null && pNode != null && cNode == pNode)  
            return false;  
  
        if (cNode != null) {  
            int i = 0;  
            /* ��ʼ�ڵ���ջ */  
            stack.push(cNode);  
            /* �������ʼ�ڵ�����յ㣬˵���ҵ�һ��·�� */  
            if (cNode == eNode)  
            {  
                /* ת������ӡ�����·��������true */  
                showAndSavePath();  
                return true;  
            }  
            /* �������,����Ѱ· */  
            else  
            {  
                /*  
                 * ���뵱ǰ��ʼ�ڵ�cNode�����ӹ�ϵ�Ľڵ㼯�а�˳������õ�һ���ڵ� 
                 * ��Ϊ��һ�εݹ�Ѱ·ʱ����ʼ�ڵ�  
                 */  
                nNode = cNode.getRelationNodes().get(i);  
                while (nNode != null) {  
                    /* 
                     * ���nNode���������ʼ�ڵ����nNode����cNode����һ�ڵ����nNode�Ѿ���ջ�� ��  
                     * ˵��������· ��Ӧ�������뵱ǰ��ʼ�ڵ������ӹ�ϵ�Ľڵ㼯��Ѱ��nNode 
                     */  
                    if (pNode != null  
                            && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {  
                        i++;  
                        if (i >= cNode.getRelationNodes().size())  
                            nNode = null;  
                        else  
                            nNode = cNode.getRelationNodes().get(i);  
                        continue;  
                    }  
                    /* ��nNodeΪ�µ���ʼ�ڵ㣬��ǰ��ʼ�ڵ�cNodeΪ��һ�ڵ㣬�ݹ����Ѱ·���� */  
                    if (getPaths(nNode, cNode, sNode, eNode))/* �ݹ���� */  
                    {  
                        /* ����ҵ�һ��·�����򵯳�ջ���ڵ� */  
                        stack.pop();  
                    }  
                    /* ��������cNode�����ӹ�ϵ�Ľڵ㼯�в���nNode */  
                    i++;  
                    if (i >= cNode.getRelationNodes().size())  
                        nNode = null;  
                    else  
                        nNode = cNode.getRelationNodes().get(i);  
                }  
                /*  
                 * ��������������cNode�����ӹ�ϵ�Ľڵ�� 
                 * ˵������cNodeΪ��ʼ�ڵ㵽�յ��·���Ѿ�ȫ���ҵ�  
                 */  
                stack.pop();  
                return false;  
            }  
        } else  
            return false;  
    }  
  @Expose
    public void setNode(Map<String, Object> parameter) throws Exception {  
        /* ����ڵ��ϵ */  
       /* int nodeRalation[][] =  
        {  
            {1},      //0  
            {0,5,2,3},//1  
            {1,4},    //2  
            {1,4},    //3  
            {2,3,5},  //4  
            {1,4}     //5  
        };*/  
          
        /* ����ڵ����� */ 
        Collection<Lru> details = lruDao.queryLru(parameter);
        //Node[] node = new Node[nodeRalation.length];  
        Node[] node = new Node[details.size()]; 
        if (null != details && details.size() > 0) {
        	int i = 0;
        	for (Lru item : details) {
        		node[i] = new Node();  
                node[i].setName(item.getKe());
                node[i].setLru(item.getText());
                i++;
                }
        }
    	if (null != details && details.size() > 0) {
    		int i = 0;
    		for (Lru item : details) {
    			Collection<Line> lines = lineDao.queryLine(parameter);
    			ArrayList<Node> List = new ArrayList<Node>();
    			if (null != lines && lines.size() > 0) {
    		    	for (Line litem : lines) {
    		    		for(int j = 0;j<node.length;j++){
    		    			if((item.getKe().equals(litem.getFroml())&&litem.getTol().equals(node[j].getName()))||(item.getKe().equals(litem.getTol())&&litem.getFroml().equals(node[j].getName())))
    		    				List.add(node[j]);  
    		    		} 
    		    	}
    			}
    			node[i].setRelationNodes(List);  
    			List = null;  //�ͷ��ڴ�
    			i++;
            }
    	}
		
    
        /*for(int i=0;i<nodeRalation.length;i++)  
        {  
            node[i] = new Node();  
            node[i].setName("node" + i);  
        } */ 
          
        /* ������ڵ�������Ľڵ㼯�� */  
        /*for(int i=0;i<nodeRalation.length;i++)  
        {  
            ArrayList<Node> List = new ArrayList<Node>();  
              
            for(int j=0;j<nodeRalation[i].length;j++)  
            {  
                List.add(node[nodeRalation[i][j]]);  
            }  
            node[i].setRelationNodes(List);  
            List = null;  //�ͷ��ڴ�  
        }*/  
  
        /* ��ʼ��������·�� */
    	if(null != parameter && !parameter.isEmpty()){
        	String begin = (String)parameter.get("begin");
        	String end = (String)parameter.get("end");
        	int sno = 0;
        	int eno = 0;
        	for(int j = 0;j<node.length;j++){
        		if(node[j].getName().equals(begin))
        			sno = j; 
        		if(node[j].getName().equals(end))
       			 	eno = j; 
        	} 
        	getPaths(node[sno], null, node[sno], node[eno]);  
    	}
        
    }  
}  
