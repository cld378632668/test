
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode(int x) : val(x), next(NULL) {}
 * };
 */
/**
C++ Clean Code
**/
class Solution {
public:
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        ListNode* head=new ListNode(-1);//存放结果的链表
        ListNode* h=head;//移动指针
        int sum=0;//每个位的加和结果
        bool carry=false;//进位标志
        // core line. 只要有一个list没到头，先尝试l1向后走（对应当前line+3），然后尝试l2向后走（对应当前line+8）
        while(l1!=NULL||l2!=NULL)  
            sum=0;
            if(l1!=NULL) 
            {
                sum+=l1->val;
                l1=l1->next;
            }
            if(l2!=NULL)
            {
                sum+=l2->val;
                l2=l2->next;
            }
            if(carry)
                sum++;
            h->next=new ListNode(sum%10);
            h=h->next;
            carry=sum>=10?true:false;
        }
        if(carry)
        {
            h->next=new ListNode(1);
        }
        return head->next;
    }
};