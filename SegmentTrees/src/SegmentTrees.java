import java.util.Scanner;

class Segment {
	
	int start;
	int end;
	
}

class Node {
	
	int sum;
	Segment segment = new Segment();
	Node lchild ;
	Node rchild ;
	
	
}

public class SegmentTrees {

	public static Node constructSegmentTree(int arr[],Segment seg) {
		
		Node node = new Node();
		node.segment.start = seg.start;
		node.segment.end = seg.end;
		
		
		if(arr.length == 1) {
			
			node.sum = arr[0];
			node.lchild = null;
			node.rchild = null;
			
		}
		
		else {
			
				int leftSubarray[] = new int[(int)Math.ceil(arr.length/2)];
				int rightSubarray[] = new int[arr.length/2];
				
				int i=0;
				for(i=0;i<leftSubarray.length;i++)
					leftSubarray[i] = arr[i];
				
				int temp = i;
				i = 0;
				for(int j=temp;i<rightSubarray.length;i++)
					rightSubarray[i] = arr[j++];
				
				Node leftChild = new Node();
				leftChild.segment.start = seg.start;
				leftChild.segment.end = seg.start + (seg.end-seg.start)/2;
				leftChild = constructSegmentTree(leftSubarray,leftChild.segment);
				
				Node rightChild = new Node();
				rightChild.segment.start = leftChild.segment.end + 1;
				rightChild.segment.end = seg.end ;
				rightChild = constructSegmentTree(rightSubarray,rightChild.segment);
				
				node.sum = leftChild.sum + rightChild.sum;
				node.lchild = leftChild;
				node.rchild = rightChild;
		
		}
		
		return node;
		
	}
	
	public static int getSum(Node root, Segment query) {
		
		if(query.end < root.segment.start || query.start > root.segment.end) 
			return 0;
		
		else if(query.start <= root.segment.start && query.end >= root.segment.end) 
			return root.sum;
		
		else if(root.segment.start == root.segment.end)
			return root.sum;
		
		else
			return getSum(root.lchild,query) + getSum(root.rchild,query);
		
	}
	
	public static int update(int position, int value, Node root) {
		
			if(position > root.segment.end || position < root.segment.start)
				return root.sum;
			
			else if((root.segment.start == root.segment.end) && 
					(root.segment.start == position)) {

				root.sum = value;
				return root.sum;
		}
			
			else {
				root.sum =  update(position,value,root.lchild) + update(position,value,root.rchild);
				return root.sum;
			}
		
		
	}

	public static void main(String[] args) {
		
		int arr[] = {10,5,30,15,50,25,70,35};
		
		Node root = new Node();
		
		Segment array = new Segment();
		
		array.start = 0;
		array.end = 7;
		
		root = constructSegmentTree(arr,array);
		
		Scanner scan = new Scanner(System.in);
		
		String choice = "";
		
		choice = scan.next();
		
		if(choice == "query") {
			
			System.out.print("Range : ");
			
			Segment query = new Segment();
			query.start = scan.nextInt();
			query.end = scan.nextInt();
			
			int segmentSum = getSum(root,query);
			System.out.println(segmentSum);
			
		}
		
		else if(choice == "update") {
			
			System.out.print("Position : ");
			int position = 0;
			position = scan.nextInt();
			
			if(position < 0 || position > 7) 
				System.out.println("Out of range");
			
			else {
				
						System.out.println();
						
						System.out.print("Value : ");
						int value = 0;
						value = scan.nextInt();
						
						System.out.println();
						
						update(position,value,root);
				
				}
			
		}
				
	}

}
