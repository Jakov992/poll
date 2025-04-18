import React, { useEffect, useState } from 'react';
import PollItem, {Poll} from './PollItem';
import {fetchPolls, submitVote} from "../api/api";

const PollList: React.FC = () => {
    const [polls, setPolls] = useState<Poll[]>([]);

    useEffect(() => {
        fetchPolls()
            .then(res => setPolls(res.data));
    }, []);

    const handleVote = (pollId: number, optionId: number) => {
        submitVote(pollId, optionId)
            .then((res) => {
                const updatedPoll: Poll = res.data;

                setPolls((prevPolls) =>
                    prevPolls.map((poll) =>
                        poll.id === updatedPoll.id ? updatedPoll : poll
                    )
                );
            });
    };


    return (
        <div className="container mx-auto">
            {polls?.map((poll) => (
                <PollItem key={poll.id} poll={poll} onVote={handleVote} />
            ))}
        </div>
    );
};

export default PollList;