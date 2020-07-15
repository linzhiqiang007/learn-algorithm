package com.flydean;

/**
 * 双倍散列 i =（base + step * h2(v)）％M
 * @author wayne
 * @version DoubleHashingProbingHashMap
 */
public class DoubleHashingProbingHashMap {

    //存储数据的节点
    static class HashNode
    {
        public int value;
        public int key;

        HashNode(int key, int value)
        {
            this.value = value;
            this.key = key;
        }
    }

    //hashNode数组，用来存储hashMap的数据
    HashNode[] hashNodes;
    int capacity;
    int size;
    final int PRIME =7;
    //代表空节点
    HashNode dummy;

    public DoubleHashingProbingHashMap(){
        this.capacity=20;
        this.size=0;
        hashNodes= new HashNode[capacity];
        //创建一个dummy节点，供删除使用
        dummy= new HashNode(-1,-1);
    }

    // 计算第一次hash
    int hash1(int key)
    {
        return (key % capacity);
    }

    // 计算第二次hash
    int hash2(int key)
    {
        return (PRIME - (key % capacity));
    }

    //插入节点
    void insertNode(int key, int value)
    {
        HashNode temp = new HashNode(key, value);

        //获取key的hashcode
        int hashIndex = hash1(key);

        //find next free space
        int i=1;
        while(hashNodes[hashIndex] != null && hashNodes[hashIndex].key != key
            && hashNodes[hashIndex].key != -1)
        {
            hashIndex=hashIndex+i*hash2(key);
            hashIndex %= capacity;
            i++;
        }

        //插入新节点，size+1
        if(hashNodes[hashIndex] == null || hashNodes[hashIndex].key == -1) {
            size++;
        }
        //将新节点插入数组
        hashNodes[hashIndex] = temp;
    }

    //删除节点
    int deleteNode(int key)
    {
        // 获取key的hashcode
        int hashIndex = hash1(key);

        //finding the node with given key
        int i=1;
        while(hashNodes[hashIndex] != null)
        {
            //找到了要删除的节点
            if(hashNodes[hashIndex].key == key)
            {
                HashNode temp = hashNodes[hashIndex];

                //插入dummy节点，表示已经被删除
                hashNodes[hashIndex] = dummy;

                // size -1
                size--;
                return temp.value;
            }
            hashIndex=hashIndex+i*hash2(key);
            hashIndex %= capacity;
            i++;
        }
        //如果没有找到，返回-1
        return -1;
    }

    //获取节点
    int get(int key)
    {
        int hashIndex = hash1(key);
        int counter=0;
        //找到我们的节点
        int i=0;
        while(hashNodes[hashIndex] != null)
        {
            if(counter++>capacity)  //设置遍历的边界条件，防止无限循环
                return -1;
            //找到了
            if(hashNodes[hashIndex].key == key){
                return hashNodes[hashIndex].value;
            }
            hashIndex=hashIndex+i*hash2(key);
            hashIndex %= capacity;
            i++;
        }
        //没找到，返回-1
        return -1;
    }

    //Return current size
    int sizeofMap()
    {
        return size;
    }

    //Return true if size is 0
    boolean isEmpty()
    {
        return size == 0;
    }

    void display()
    {
        for(int i=0 ; i<capacity ; i++)
        {
            if(hashNodes[i] != null && hashNodes[i].key != -1)
                System.out.println("key = "+hashNodes[i].key+ " value= "+hashNodes[i].value);
        }
    }

    public static void main(String[] args) {
        DoubleHashingProbingHashMap doubleHashingProbingHashMap= new DoubleHashingProbingHashMap();
        doubleHashingProbingHashMap.insertNode(20,20);
        doubleHashingProbingHashMap.insertNode(50,50);
        doubleHashingProbingHashMap.insertNode(100,100);
        doubleHashingProbingHashMap.display();
    }

}
