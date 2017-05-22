# Who: Junda Lou
# What: BinarySeach.asm
# Why: Insert integers onto the stack, sort them and search for a certain int
# When: 5.13.2017
# How: Using the stack pointer instead of an array and perform a binary search
	
.data
Prompt1:	.asciiz "Please enter the quantity of integers: "
Prompt2:	.asciiz "Plase enter the integer: "
Prompt3:	.asciiz "Your sorted integers: "
Prompt4:	.asciiz "\nEnter the integer you want to search for: "
Result1:	.asciiz "Integer Not Found!"
Result2:	.asciiz "Integer Found!"
Errormsg:	.asciiz "Error!"
WhiteSpace:	.asciiz " "

# t0 = control the quantity of ints, tmp in BinarySearch
# t1 = record the initial position of stack every insertion, tmp in BinarySearch
# t2 = tmp for interchange values, mid address
# t3 = tmp, stack[mid]
# t5 = always point to the top of the stack
# a0 = int to search for
# a1 = high
# a2 = low
# a3 = starting index in the stack for $ra

# a regs can be not stored onto the stack becasue it will only go to one direction in the recursion of the subroutine. And when return is hit, it will go back directly to the origin call. Only the return address $ra is needed for every subroutine
	
.text
.globl main
main:
	la $a0, Prompt1
	li $v0, 4
	syscall

	li $v0, 5
	syscall
	blez $v0, Error

	move $t0, $v0	    # get quantity
	move $t5, $sp       # get the top of the stack
	
Loop:
	la $a0, Prompt2    
	li $v0, 4
	syscall

	li $v0, 5
	syscall
	move $a0, $v0
	jal Insertion
	addiu $t0, $t0, -1
	bne $t0, 0, Loop
	j PrintStack

Insertion:
	move $t1, $sp	  # record the initial position
	sw $a0, 0($sp)   
	beq $sp, $t5, Return  # go return the first time
	addiu $sp, $sp, 4   # otherwise go up
	lw $t3, 0($sp)     # if "up" < a0, move up a0
	blt $t3, $a0, MoveUp
	j Return

MoveUp:	
	lw $t2, 0($sp)
	sw $a0, 0($sp)
	addiu $sp, $sp, -4   # interchange value
	sw $t2, 0($sp)
	addiu $sp, $sp, 4
	beq $sp, $t5, Return   # go return if reach top
	addiu $sp, $sp, 4
	lw $t3, 0($sp)
	blt $t3, $a0, MoveUp  # move up again if needed

Return:
	move $sp, $t1    # back to the initial position of the current subroutine
	addiu $sp, $sp, -4   # go down, make room for the next insertion
	jr $ra

PrintStack:
	addiu $sp, $sp, 4
	la $a0, Prompt3
	li $v0, 4
	syscall

PrintLoop:
	lw $a0, 0($sp)
	li $v0, 1
	syscall
	addiu $sp, $sp, 4
	la $a0, WhiteSpace
	li $v0, 4
	syscall
	bne $sp, $t5, PrintLoop    # Print sorted ints as in lab3
	lw $a0, 0($sp)
	li $v0, 1
	syscall
	
	la $a0, Prompt4    # Print prompt for user to enter the int to be searched
	li $v0, 4
	syscall
	li $v0, 5
	syscall

	move $a0, $v0     # pass in params
	move $a1, $t5     # a0 = int to be searched, a1 = top of stack(high)
	move $a2, $t1     # a2 = bottom of stack(low), a3 = starting index for storing $ra
	addiu $a3, $t1, -4
	jal BinarySearch
	beq $v0, 0, ShowResult1
	beq $v0, 1, ShowResult2

BinarySearch:
	move $sp, $a3         # store $ra onto the stack
	sw $ra, 0($sp)
	addiu $a3, $a3, -4    # make room for the next
	divu $t0, $a2, 4      # compare "index" to avoid problems caused by 4 byte increment
	divu $t1, $a1, 4      # can be deleted I think
	bgt $t0, $t1, Return2    # return if low > high
	
	addu $t2, $a1, $a2    # get mid = (low + high) / 2
	divu $t2, $t2, 2
	remu $t0, $t2, 4     # Special case: if there is no middle index between the low and high, result will be ended with a 2, which will cause an alignment error. To fix it, add by 2
	beq $t0, 2, Shift
	j BinarySearchCont
Shift:
	addiu $t2, $t2, 2
	j BinarySearchCont   # go to the next half. Cut here because of the Shift branch

BinarySearchCont:	
	move $sp, $t2          # get the element in the stack[mid]
	lw $t3, 0($sp)         
	beq $a0, $t3, Return1     # compare to the int to search for
	blt $t3, $a0, Recursion1   # go search mid + 1 to high
	bgt $t3, $a0, Recursion2   # go search low to mid - 1	

Recursion1:
	move $a2, $t2         # low = mid + 1
	addiu $a2, $a2, 4
	jal BinarySearch      # return will be hit, back to here
	beq $v0, 1, Return1   # when return is hit, return again and again back to the origin
	beq $v0, 0, Return2

Recursion2:
	move $a1, $t2
	addiu $a1, $a1, -4   # high = mid - 1
	jal BinarySearch
	beq $v0, 1, Return1
	beq $v0, 0, Return2

Return1:
	addiu $a3, $a3, 4   # move up 1 position because of the reserved room for next
	move $sp, $a3       # get $ra
	lw $ra, 0($sp)
	li $v0, 1   	    # integer found
	jr $ra              # return it

Return2:
	addiu $a3, $a3, 4
	move $sp, $a3
	lw $ra, 0($sp)
	li $v0, 0           # integer not found
	jr $ra

ShowResult1:
	la $a0, Result1    # not found
	li $v0, 4
	syscall
	j End

ShowResult2:
	la $a0, Result2    # found
	li $v0, 4
	syscall
	j End

Error:
	la $a0, Errormsg
	li $v0, 4
	syscall

End:
	li $v0, 10
	syscall
