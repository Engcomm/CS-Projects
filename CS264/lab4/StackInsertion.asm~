# Who: Junda Lou
# What: StackInsertion.asm
# Why: Insert integers onto the stack
# When: 5.13.2017
# How: Using the stack pointer instead of an array
	
.data
Prompt1:	.asciiz "Please enter the quantity of integers: "
Prompt2:	.asciiz "Plase enter the integer: "
Prompt3:	.asciiz "Your sorted integers: "
Errormsg:	.asciiz "Error!"
WhiteSpace:	.asciiz " "

# t0 = control the quantity of ints
# t1 = record the initial position of stack every insertion
# t2 = tmp for interchange values
# t3 = tmp
# t5 = always point to the top of the stack 
	
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
	bne $sp, $t5, PrintLoop
	lw $a0, 0($sp)
	li $v0, 1
	syscall
	j End

Error:
	la $a0, Errormsg
	li $v0, 4
	syscall

End:
	li $v0, 10
	syscall
