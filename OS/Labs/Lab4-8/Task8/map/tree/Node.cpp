#include <iostream>
#include "Node.h"

int *Node::getKey() {
    return key;
}

int *Node::getValue() {
    return value;
}

Node *Node::findNode(int key) {
    if (key == *(this->key)) {
        return this;
    } else if (key > *(this->key)) {
        if (rightNode != nullptr) {
            return rightNode->findNode(key);
        } else {
            return nullptr;
        }
    } else {
        if (leftNode != nullptr) {
            return leftNode->findNode(key);
        } else {
            return nullptr;
        }
    }

}

void Node::removeRightNode() {
    if (rightNode != nullptr) {
        Node *backupNode = rightNode;
        if (rightNode->leftNode != nullptr && rightNode->rightNode != nullptr) {
            rightNode = rightNode->rightNode;
            rightNode->insertNode(backupNode->leftNode);
        } else {
            if (rightNode->leftNode != nullptr) {
                rightNode = rightNode->leftNode;
            } else {
                rightNode = rightNode->rightNode;
            }
        }
        backupNode->leftNode = nullptr;
        backupNode->rightNode = nullptr;
        backupNode->~Node();
    }
}

void Node::removeLeftNode() {
    if (leftNode != nullptr) {
        Node *backupNode = leftNode;
        if (leftNode->leftNode != nullptr && leftNode->rightNode != nullptr) {
            leftNode = leftNode->rightNode;
            leftNode->insertNode(backupNode->leftNode);
        } else {
            if (leftNode->leftNode != nullptr) {
                leftNode = leftNode->leftNode;
            } else {
                leftNode = leftNode->rightNode;
            }
        }
        backupNode->leftNode = nullptr;
        backupNode->rightNode = nullptr;
        backupNode->~Node();
    }
}

void Node::insertNode(Node *node) {
    if (*key != *(node->key)) {
        if (*(node->key) > *key) {
            if (rightNode != nullptr) {
                rightNode->insertNode(node);
            } else {
                rightNode = node;
            }
        } else {
            if (leftNode != nullptr) {
                leftNode->insertNode(node);
            } else {
                leftNode = node;
            }
        }
    }
}

bool Node::removeNode(int key) {
    if (key > *(this->key)) {
        if (rightNode != nullptr) {
            if (key == *(rightNode->key)) {
                this->removeRightNode();
            } else {
                return rightNode->removeNode(key);
            }
        } else return false;
    } else {
        if (leftNode != nullptr) {
            if (key == *(leftNode->key)) {
                this->removeLeftNode();
            } else {
                return leftNode->removeNode(key);
            }
        } else return false;
    }
}

Node::Node(int key, int val) {
    this->key = new int(key);
    value = new int(val);
    leftNode = nullptr;
    rightNode = nullptr;
}

Node::~Node() {
    std::cout << "Node destructor, key=" << *key << std::endl;
    delete key;
    delete value;
    if (leftNode != nullptr)leftNode->~Node();
    if (rightNode != nullptr)rightNode->~Node();
}

Node *Node::getLeftNode() {
    return leftNode;
}

Node *Node::getRightNode() {
    return rightNode;
}

void Node::setKey(int k) {
    key = new int(k);
}

void Node::setValue(int v) {
    value = new int(v);
}

Node::Node() {

}

void Node::setLeftNode(Node* node) {
    leftNode = node;
}

void Node::setRightNode(Node* node) {
    rightNode = node;
}










