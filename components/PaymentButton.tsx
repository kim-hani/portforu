import React from 'react';
import { requestPayment } from '../frontend/src/lib/toss/tossPayment';

interface Props {
    subscribeId: number;
    amount: number;
    memberName: string;
}

const PaymentButton = ({ subscribeId, amount, memberName }: Props) => {
    const handleClick = () => {
        requestPayment({ subscribeId, amount, memberName });
    };

    return <button onClick={handleClick}>결제하기</button>;
};

export default PaymentButton;
