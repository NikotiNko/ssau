#pragma once
#ifdef LABDLL_EXPORTS
#define LABDLL_API __declspec(dllexport)
#else
#define LABDLL_API __declspec(dllimport)
#endif


class __declspec(dllexport) Node {
public:
    int* getKey();

    int* getValue();

    void setKey(int k);

    void setValue(int v);

    Node *findNode(int key);

    void removeLeftNode();

    void removeRightNode();

    Node* getLeftNode();

    Node* getRightNode();

    void setLeftNode(Node* node);

    void setRightNode(Node* node);

    void insertNode(Node* node);

    bool removeNode(int key);

    Node(int key, int val);

    Node();

    ~Node();

private:
    int* key;
    int* value;
    Node* leftNode;
    Node* rightNode;
};
